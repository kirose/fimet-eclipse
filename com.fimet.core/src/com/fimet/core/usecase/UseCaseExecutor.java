package com.fimet.core.usecase;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.internal.ui.util.CoreUtility;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.undo.CreateFileOperation;
import org.eclipse.ui.ide.undo.WorkspaceUndoUtil;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;

import com.fimet.commons.console.Console;
import com.fimet.commons.exception.UseCaseException;
import com.fimet.commons.messages.Messages;
import com.fimet.commons.preference.IPreference;
import com.fimet.commons.utils.ByteUtils;
import com.fimet.commons.utils.ThreadUtils;
import com.fimet.core.Activator;
import com.fimet.core.IExtractorManager;
import com.fimet.core.ILogManager;
import com.fimet.core.IPreferencesManager;
import com.fimet.core.IReportManager;
import com.fimet.core.ITransactionLogManager;
import com.fimet.core.IUseCaseManager;
import com.fimet.core.IUseCaseReportManager;
import com.fimet.core.Manager;
import com.fimet.core.ISO8583.adapter.IByteArrayAdapter;
import com.fimet.core.ISO8583.parser.Message;
import com.fimet.core.entity.sqlite.UseCaseReport;
import com.fimet.core.entity.sqlite.pojo.Notice;
import com.fimet.core.net.IMessenger;
import com.fimet.core.net.IMessengerManager;
import com.fimet.core.net.ISocket;
import com.fimet.core.net.listeners.IMessengerListener;
import com.fimet.core.net.listeners.IMessengerComplete;
import com.fimet.core.net.listeners.IMessengerParseAcquirerResponse;
import com.fimet.core.net.listeners.IMessengerParseIssuerRequest;
import com.fimet.core.net.listeners.IMessengerReadAcquirerResponse;
import com.fimet.core.net.listeners.IMessengerReadIssuerRequest;
import com.fimet.core.net.listeners.IMessengerSimulateResponse;
import com.fimet.core.net.listeners.IMessengerWriteAcquirerRequest;
import com.fimet.core.net.listeners.IMessengerWriteIssuerResponse;
import com.fimet.core.net.listeners.IMessengerConnected;
import com.fimet.core.net.listeners.IMessengerDisconnected;

/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class UseCaseExecutor implements
	IMessengerConnected,
	IMessengerDisconnected,
	IMessengerComplete,
	IMessengerParseAcquirerResponse,
	IMessengerParseIssuerRequest,
	IMessengerReadAcquirerResponse,
	IMessengerReadIssuerRequest,
	IMessengerSimulateResponse,
	IMessengerWriteAcquirerRequest,
	IMessengerWriteIssuerResponse
{

	private static final int TIME_EXCECUTION_USE_CASE_DEFAULT = 6*1000;
	protected static final int STARTED = 1;
	protected static final int RUNNING = 2;
	protected static final int STOPED = 4;
	protected static final int COMPLETING = 8;
	protected static final int COMPLETED = 16;

	private static ILogManager logManager = Manager.get(ILogManager.class);
	private static IExtractorManager extractorManager = Manager.get(IExtractorManager.class);
	private static IPreferencesManager preferencesManager = Manager.get(IPreferencesManager.class);
	private static IMessengerManager messengerManager = Manager.get(IMessengerManager.class);
	private static ITransactionLogManager transactionLogManager = Manager.get(ITransactionLogManager.class);
	
	private boolean acquirerConnected = false;
	private boolean issuerConnected = false;
	private IMessenger issuerConnection;
	private IMessenger acquirerConnection;
	private UseCase useCase;
	private IResource resource;
	private UseCaseJobListener job;
	private AtomicInteger status = new AtomicInteger(0);
	protected OnFinishListener onFinishListener;
	private String errorMessage;
	private int timeExcecutionUsecase;
	private UseCaseReport report;
	
	UseCaseExecutor(IResource resource, UseCaseJobListener job) {
		this(resource,job,null);
	}
	UseCaseExecutor(IResource resource, UseCaseJobListener job, OnFinishListener finishListener) {
		this.resource = resource;
		this.acquirerConnected = false;
		this.issuerConnected = false;
		this.job = job;
		this.onFinishListener = finishListener;
		this.timeExcecutionUsecase = TIME_EXCECUTION_USE_CASE_DEFAULT;
	}

	void execute() {
		try {
			useCase = buildUseCase();
			setStatus(STARTED);
			if (useCase.getAcquirer().getRequest().getAuthorization() != null) {
				UseCaseExecutor taskAuth = new UseCaseExecutorAuthorization(this);
				taskAuth.execute();
			} else {
				run();
			}
		} catch (Exception e) {
			Activator.getInstance().error("Executing Use Case "+getName(), e);
			setErrorMessage(e.getMessage());
			stop();
		}
	}
	protected UseCase buildUseCase() {
		useCase = Manager.get(IUseCaseManager.class).parseForExecution(resource);
		if (useCase.getAcquirer().getConnection() == null) {
			throw new UseCaseException(useCase.getName() + " Acqurier is null");
		}
		if ((useCase.getExecutionTime() != null && useCase.getExecutionTime() > 0)) {
			timeExcecutionUsecase = (useCase.getExecutionTime() + 2) * 1000;
		} else if (
			useCase.getIssuer() != null && 
			useCase.getIssuer().getResponse() != null && 
			useCase.getIssuer().getResponse().getTimeout() != null && 
			useCase.getIssuer().getResponse().getTimeout() > 0
		) {
			timeExcecutionUsecase = (useCase.getIssuer().getResponse().getTimeout() + 2) * 1000;
		}
		
		return useCase;
	}
	protected void run() {
		acquirerConnection = messengerManager.getConnection(useCase.getAcquirer().getConnection());
		acquirerConnection.addListener(IMessengerListener.ON_DISCONNECTED, this);
		if (useCase.getIssuer() != null && useCase.getIssuer().getConnect()) {
			issuerConnection = messengerManager.getConnection(useCase.getIssuer().getConnection());
			issuerConnection.addListener(IMessengerListener.ON_DISCONNECTED, this);
		}
		if (acquirerConnection.isConnected() && (issuerConnection == null || (issuerConnection != null && issuerConnection.isConnected()))) {
			UseCaseExecutor.this._run();
		} else {
			if (!acquirerConnection.isConnected()) {
				acquirerConnection.addListener(IMessengerListener.ON_CONNECTED, this);
			} else {
				this.issuerConnected = true;
			}
			if (issuerConnection != null) {
				if (!issuerConnection.isConnected()) {
					issuerConnection.addListener(IMessengerListener.ON_CONNECTED, this);
				} else {
					this.issuerConnected = true;
				}
				issuerConnection.connect();
			}
			acquirerConnection.connect();
		}
	}
	@Override
	public void onMessangerConnected(IMessenger connection) {
		if (connection == acquirerConnection) {
			acquirerConnected = true;
		} else if (connection == issuerConnection) {
			issuerConnected = true;
		}
		if (acquirerConnected && issuerConnected) {
			_run();
		}
	}
	@Override
	public void onMessangerDisconnected(IMessenger connection) {
		stop();
	}
	private void _run() {
		onStart();

		createFolder();

		addListeners();
		
		Message msg = useCase.getAcquirer().getRequest().getMessage();
		try {
			useCase.getValidator().onAcquirerRequest(msg);
		} catch (Throwable e) {
			Console.getInstance().error(UseCaseExecutor.class,"Error in "+useCase.getName()+" validator.onResponseAcquirer\n"+e.getMessage());
			Activator.getInstance().error("Error in "+useCase.getName()+" validator.onResponseAcquirer", e);
		}
		try {
			acquirerConnection.wirteMessage(msg);
			new TimeoutValidator(this).start();
		} catch (Exception e) {
			stop();
		}

	}
	private void addListeners() {
		acquirerConnection.addListener(IMessengerListener.ON_SIMULATE_ACQ_REQUEST, this);
		acquirerConnection.addListener(IMessengerListener.ON_WRITE_ACQ_REQUEST, this);
		acquirerConnection.addListener(IMessengerListener.ON_READ_ACQ_RESPONSE, this);
		acquirerConnection.addListener(IMessengerListener.ON_PARSE_ACQ_RESPONSE, this);
		acquirerConnection.addListener(IMessengerListener.ON_COMPLETE, this);
		if (issuerConnection != null) {
			issuerConnection.addListener(IMessengerListener.ON_READ_ISS_REQUEST, this);
			issuerConnection.addListener(IMessengerListener.ON_PARSE_ISS_REQUEST, this);
			issuerConnection.addListener(IMessengerListener.ON_SIMULATE_ISS_RESPONSE, this);
			issuerConnection.addListener(IMessengerListener.ON_WRITE_ISS_RESPONSE, this);
		}
	}
	protected void removeListeners() {
		if (acquirerConnection != null) {
			acquirerConnection.removeListener(IMessengerListener.ON_CONNECTED, this);
			acquirerConnection.removeListener(IMessengerListener.ON_SIMULATE_ACQ_REQUEST, this);
			acquirerConnection.removeListener(IMessengerListener.ON_WRITE_ACQ_REQUEST, this);
			acquirerConnection.removeListener(IMessengerListener.ON_READ_ACQ_RESPONSE, this);
			acquirerConnection.removeListener(IMessengerListener.ON_PARSE_ACQ_RESPONSE, this);
			acquirerConnection.removeListener(IMessengerListener.ON_COMPLETE, this);
			acquirerConnection.removeListener(IMessengerListener.ON_DISCONNECTED, this);
		}
		if (issuerConnection != null) {
			issuerConnection.removeListener(IMessengerListener.ON_CONNECTED, this);
			issuerConnection.removeListener(IMessengerListener.ON_READ_ISS_REQUEST, this);
			issuerConnection.removeListener(IMessengerListener.ON_PARSE_ISS_REQUEST, this);
			issuerConnection.removeListener(IMessengerListener.ON_SIMULATE_ISS_RESPONSE, this);
			issuerConnection.removeListener(IMessengerListener.ON_WRITE_ISS_RESPONSE, this);
			issuerConnection.removeListener(IMessengerListener.ON_DISCONNECTED, this);
		}
	}
	private void createFolder() {
		IPath path = createPathFolder();
		IWorkspaceRoot root = useCase.getResource().getProject().getWorkspace().getRoot();
		IFolder folder = root.getFolder(path);
		try {
			if (folder.exists()) {
				if (askOverride(useCase.getName())) {
					folder.delete(true, new NullProgressMonitor());
					CoreUtility.createFolder(folder, true, true, new NullProgressMonitor());
				}
			} else {
				CoreUtility.createFolder(folder, true, true, new NullProgressMonitor());
			}
		} catch (CoreException e) {}
		useCase.setFolder(folder);
	}
	private boolean askOverride(String name) {
		//IDEWorkbenchPlugin.getDefault().getPreferenceStore().setValue(IPreference.RUN_USECASE_OVERRIDE_FOLDER, false);
		if (PlatformUI.getWorkbench() != null && PlatformUI.getWorkbench().getActiveWorkbenchWindow() != null) {
			boolean overrideFile = Activator.getInstance().getPreferenceStore().getBoolean(IPreference.RUN_USECASE_OVERRIDE_FOLDER);
			if (overrideFile) {
				return true;
			}
			MessageDialogWithToggle dialog = MessageDialogWithToggle.openOkCancelConfirm(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
				"Override Folder",
				"Already exist "+name+", do you want override it?",
				"Don't ask again.",
				false,
				Activator.getInstance().getPreferenceStore(),
				IPreference.RUN_USECASE_OVERRIDE_FOLDER
			);
			overrideFile = dialog.getReturnCode() == Window.OK;
			if (dialog.getToggleState() && overrideFile) {
				Activator.getInstance().getPreferenceStore().setValue(IPreference.RUN_USECASE_OVERRIDE_FOLDER, overrideFile);
			}
			return overrideFile;
		} else {
			return true;
		} 
	}

	private IPath createPathFolder() {
		String fullPath = useCase.getResource().getLocation().toString();
		int index = fullPath.indexOf(useCase.getResource().getProject().getName());
		String pathRelative = fullPath.substring(index+useCase.getResource().getProject().getName().length()+1).replace('\\', '/');
		pathRelative = pathRelative.substring(0, pathRelative.lastIndexOf('.'));
		index = pathRelative.lastIndexOf('/');
		if (index != -1) {
			pathRelative = pathRelative.substring(0,index);			
		}
		String[] folders = pathRelative.split("/");
		IPath path = new Path(useCase.getResource().getProject().getName()).makeAbsolute();
		if (folders != null && folders.length > 0) {
			for (String folder : folders) {
				path = path.append(folder);
			}
		}
		path = path.append(useCase.getName());
		return path;
		
	}
	private void createSimQueue(String nameSimqueue, byte[] bytes) {
		ThreadUtils.runOnMainThread(()->{
			IPath newFilePath = useCase.getFolder().getFullPath().append(nameSimqueue+"."+"sim_queue");
			final IFile newFileHandle = IDEWorkbenchPlugin.getPluginWorkspace().getRoot().getFile(newFilePath);
			final InputStream initialContents = new ByteArrayInputStream(ByteUtils.append(bytes, (byte)10));
			CreateFileOperation op1 = new CreateFileOperation(newFileHandle, null, initialContents, "new sim_queue");
			try {
				op1.execute(new NullProgressMonitor(), WorkspaceUndoUtil.getUIInfoAdapter(PlatformUI.getWorkbench().getDisplay().getActiveShell()));
			} catch (final ExecutionException e) {
				Activator.getInstance().error("Cannot create sim_queue: " + nameSimqueue, e);
			}
		});
	}
	public UseCase getUseCase() {
		return useCase;
	}
	public void onStart() {
		setStatus(RUNNING);
		Console.getInstance().info(UseCaseExecutor.class, "\nRunning UseCase " + getUseCase().getName()+"\n");
		job.onStart(this);
	}
	/**
	 * Se invoca cuando se recive una respuesta del adquirente
	 */
	@Override
	public void onMessengerComplete() {
		setStatus(COMPLETED);
		Console.getInstance().info(UseCaseExecutor.class,"\nComplete UseCase " + getUseCase().getName()+"\n");
		job.onComplete(this);
		onFinish();
	}
	public void stop() {
		setStatus(STOPED);
		Console.getInstance().info(UseCaseExecutor.class,"\nStoping UseCase " + getName()+"\n");
		job.onStop(this);
		onFinish();
	}
	public void onTimeout() {
		setStatus(STOPED);
		Console.getInstance().info(UseCaseExecutor.class,"\nTimeout UseCase " + getName()+"\n");
		useCase.addNotice(new Notice(Notice.ERROR, "Execution result in timeout"));
		job.onTimeout(this);
		onFinish();
	}
	public void onFinish() {
		removeListeners();
		//Console.getInstance().info("\n\nFinish UseCase " + getUseCase().getName()+"\n");
		job.onFinish(this);
		if (onFinishListener != null) {
			onFinishListener.onFinish(this);
		}
		saveReport();
		createEvidences(useCase);
	}
	protected void createEvidences(UseCase useCase) {
		if (useCase != null) {
			if (extractorManager != null && preferencesManager.getBoolean(IPreferencesManager.EXTRACTOR_ENABLE, false)) {
				extractorManager.manage(useCase);
			}
			if (logManager != null && preferencesManager.getBoolean(IPreferencesManager.LOG_ENABLE, false)) {
				logManager.manage(useCase);
			}
		}
	}
	public boolean isStarted() {
		return status.get() == STARTED;
	}
	public boolean isStoped() {
		return status.get() == STOPED;
	}
	public boolean isCompleted() {
		return status.get() == COMPLETED;
	}
	public boolean isCompleting() {
		return status.get() == COMPLETING;
	}
	public boolean isRunning() {
		return status.get() == RUNNING;
	}
	public void setStatus(int status) {
		this.status.set(status);
	}
	public boolean hasError() {
		return errorMessage != null;
	}
	private void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	protected UseCaseJobListener getJob() {
		return job;
	}
	public IResource getResource() {
		return resource;
	}
	public static interface OnFinishListener{
		void onFinish(UseCaseExecutor task);
	}
	private class TimeoutValidator extends Thread {
		UseCaseExecutor task;
		TimeoutValidator(UseCaseExecutor task){
			this.task = task;
		}
		@Override
		public void run() {
			try {
				Thread.sleep(timeExcecutionUsecase);
				if (task.isRunning()) {
					task.onTimeout();
				}
			} catch (InterruptedException | ThreadDeath e) {
				Activator.getInstance().error("Timeout Use Case "+task.getUseCase().getName(), e);
			}
		}
	}
	public String getName() {
		return resource.getName().substring(0, resource.getName().lastIndexOf('.'));
	}
	public IProject getProject() {
		return resource.getProject();
	}
	@Override
	public void onMessangerWriteIssuerResponse(IMessenger conn, byte[] msg) {
		if (!isStoped() && preferencesManager.getBoolean(IPreferencesManager.CREATE_SIMQUEUE_WRITE_ISSUER, true)) {
			//Console.getInstance().debug(UseCaseExecutor.class.getName(), "Write issuer response sim_queue");
			if (conn.getConnection().getAdapter() instanceof IByteArrayAdapter) {
				IByteArrayAdapter adapter = (IByteArrayAdapter)conn.getConnection().getAdapter();
				createSimQueue(Messages.UseCase_IssuerResponse, adapter.writeByteArray(msg));
			} else {
				Console.getInstance().warning(UseCaseExecutor.class, "Cannot create sim_queue for issuer "+conn.getConnection().getName()+", the adapter must implements IByteArrayAdapter");
			}
		}
	}
	@Override
	public void onMessangerReadIssuerRequest(IMessenger conn, byte[] msg) {
		if (!isStoped() && preferencesManager.getBoolean(IPreferencesManager.CREATE_SIMQUEUE_READ_ISSUER, true)) {
			//Console.getInstance().debug(UseCaseExecutor.class.getName(), "Write issuer request sim_queue");
			if (conn.getConnection().getAdapter() instanceof IByteArrayAdapter) {
				IByteArrayAdapter adapter = (IByteArrayAdapter)conn.getConnection().getAdapter();
				createSimQueue(Messages.UseCase_IssuerRequest, adapter.writeByteArray(msg));
			} else {
				Console.getInstance().warning(UseCaseExecutor.class, "Cannot create sim_queue for issuer "+conn.getConnection().getName()+", the adapter must implements IByteArrayAdapter");
			}
		}
	}
	@Override
	public void onMessangerParseIssuerRequest(Message request) {
		if (useCase.getIssuer() != null && useCase.getIssuer().getRequest() != null && useCase.getIssuer().getRequest().getMessage() == null) {
			useCase.getIssuer().getRequest().setMessage(request);
			try {
				useCase.getValidator().onIssuerRequest(request);
			} catch (Throwable e) {
				Console.getInstance().error(UseCaseExecutor.class, "Error in "+useCase.getName()+" validator.onResponseAcquirer\n"+e.getMessage());
				Activator.getInstance().error("Error in "+useCase.getName()+" validator.onResponseAcquirer", e);
			}
		}
	}
	@Override
	public Integer onMessangerSimulateResponse(Message response) {
		useCase.getIssuer().getResponse().setMessage(response);
		try {
			useCase.getValidator().onIssuerResponse(response);
		} catch (Throwable e) {
			Console.getInstance().error(UseCaseExecutor.class, "Error in "+useCase.getName()+" validator.onResponseAcquirer\n"+e.getMessage());
			Activator.getInstance().error("Error in "+useCase.getName()+" validator.onResponseAcquirer", e);
		}
		if (useCase.getIssuer() != null && useCase.getIssuer().getResponse() != null) {
			useCase.getIssuer().getResponse().excludeFields(response);
			useCase.getIssuer().getResponse().includeFields(response);
			useCase.getIssuer().getResponse().setMessage(response);
			return useCase.getIssuer().getResponse().getTimeout();
		}
		return 0;
	}
	@Override
	public void onMessangerWriteAcquirerRequest(IMessenger conn, byte[] msg) {
		if (preferencesManager.getBoolean(IPreferencesManager.CREATE_SIMQUEUE_WRITE_ACQUIRER, true)) {
			if (conn.getConnection().getAdapter() instanceof IByteArrayAdapter) {
				IByteArrayAdapter adapter = (IByteArrayAdapter)conn.getConnection().getAdapter();
				createSimQueue(Messages.UseCase_AcquirerRequest, adapter.writeByteArray(msg));
			} else {
				Console.getInstance().warning(UseCaseExecutor.class, "Cannot create sim_queue for aquirer "+conn.getConnection().getName()+", the adapter must implements IByteArrayAdapter");
			}
		}
	}
	@Override
	public void onMessangerReadAcquirerResponse(IMessenger conn, byte[] msg) {
		if (!isStoped()) {
			setStatus(COMPLETING);
			if (preferencesManager.getBoolean(IPreferencesManager.CREATE_SIMQUEUE_READ_ACQUIRER, true)) {
				if (conn.getConnection().getAdapter() instanceof IByteArrayAdapter) {
					IByteArrayAdapter adapter = (IByteArrayAdapter)conn.getConnection().getAdapter();
					createSimQueue(Messages.UseCase_AcquirerResponse, adapter.writeByteArray(msg));
				} else {
					Console.getInstance().warning(UseCaseExecutor.class, "Cannot create sim_queue for aquirer "+conn.getConnection().getName()+", the adapter must implements IByteArrayAdapter");
				}
			}
		}
	}
	@Override
	public void onMessangerParseAcquirerResponse(Message response) {
		useCase.getAcquirer().getResponse().setMessage(response);
		try {
			useCase.getValidator().onAcquirerResponse(response);
		} catch (Throwable e) {
			Console.getInstance().error(UseCaseExecutor.class, "Error in "+useCase.getName()+" validator.onResponseAcquirer\n"+e.getMessage());
			Activator.getInstance().error("Error in "+useCase.getName()+" validator.onResponseAcquirer", e);
		}
	}
	public UseCaseReport getReport() {
		return report;
	}
	public void setReport(UseCaseReport report) {
		this.report = report;
	}
	private void saveReport() {
		if (report == null && useCase != null) {
			String timestamp = transactionLogManager != null ? transactionLogManager.findLastTimestamp(useCase.getAcquirer().getConnection()) : null;
			useCase.setTimestamp(timestamp);
			report = new UseCaseReport(getProject().getName(),getResource().getLocation().toString(),getName());
			ISocket acquirer = useCase.getAcquirer().getConnection();
			report.setAcquirer(acquirer.getName()+"\n"+acquirer.getAddress()+"\n"+acquirer.getPort()+"\n"+acquirer.getAdapter().getName());
			report.setValidations(useCase.getValidationResults());
			report.put(UseCase.TIMESTAMP, timestamp);
			
			Manager.get(IReportManager.class).getReportDataMapper().map(useCase, report);
	
			if (useCase.getAcquirer().getResponse() != null) {
				if (useCase.getAcquirer().getResponse().getMessage() != null) {
					Message response = useCase.getAcquirer().getResponse().getMessage();
					if (response.hasField(39)) {
						report.setResponseCode(response.getValue(39));
					}
				}
			}
			if (useCase.getIssuer() != null) {
				if (useCase.getIssuer().getConnection() != null) {
					ISocket issuer = useCase.getIssuer().getConnection();
					report.setIssuer(issuer.getName()+"\n"+issuer.getAddress()+"\n"+issuer.getPort()+"\n"+issuer.getAdapter().getName());
				}
			}
		}
		Manager.get(IUseCaseReportManager.class).save(report);
	}
}
