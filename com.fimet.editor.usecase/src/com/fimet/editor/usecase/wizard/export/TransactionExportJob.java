package com.fimet.editor.usecase.wizard.export;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Properties;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.osgi.service.resolver.State;
import org.eclipse.pde.core.IModel;
import org.eclipse.pde.internal.core.PDECore;
import org.eclipse.pde.internal.core.PDECoreMessages;
import org.eclipse.pde.internal.core.exports.WorkspaceExportHelper;
import org.eclipse.swt.graphics.Path;

import com.fimet.commons.exception.ParserException;
import com.fimet.commons.io.FileUtils;
import com.fimet.commons.messages.Messages;
import com.fimet.commons.utils.ByteUtils;
import com.fimet.core.IUseCaseManager;
import com.fimet.core.Manager;
import com.fimet.core.ISO8583.parser.Message;
import com.fimet.core.impl.Activator;
import com.fimet.core.usecase.UseCase;
import com.fimet.editor.usecase.wizard.export.TransactionExportInfo.TransactionModel;
import com.fimet.parser.util.ParserUtils;

public class TransactionExportJob extends Job {
	// Location where the build takes place
	protected String fBuildTempLocation;
	protected String fBuildTempMetadataLocation;
	private static boolean fHasErrors;

	protected State fStateCopy;

	private static final String EXTENSION_SIM_QUEUE = "sim_queue";
	protected TransactionExportInfo fInfo;
	
	public TransactionExportJob(TransactionExportInfo info, String name) {
		super(name);
		fInfo = info;
		fBuildTempLocation = PDECore.getDefault().getStateLocation().append("temp").toString();
	}


	@Override
	protected IStatus run(IProgressMonitor monitor) {
		try {
			createDestination();
			SubMonitor subMonitor = SubMonitor.convert(monitor, "Exporting...", (fInfo.getFiles().length * 23) + 5 + 10);
			IStatus status = testBuildWorkspaceBeforeExport(subMonitor.split(10));
			if (fInfo.zip) {
				throw new RuntimeException("Not yet implemented");
			} else if (fInfo.singleFile) { 
				doExportSingleFile(new File(fInfo.destinationDirectory+File.separator+fInfo.fileName), fInfo.getFiles(), subMonitor);
			} else {
				for (TransactionModel item : fInfo.getFiles()) {
					if (monitor.isCanceled())
						return Status.CANCEL_STATUS;
					try {
						doExport(item, subMonitor.split(20));
					} catch (CoreException e) {
						return e.getStatus();
					}
				}
			
				// Generate Zip ...
			}
			return status;
		} catch (InvocationTargetException e) {
			return new Status(IStatus.ERROR, PDECore.PLUGIN_ID, PDECoreMessages.FeatureBasedExportOperation_ProblemDuringExport, e.getCause() != null ? e.getCause() : e);
		} catch (CoreException e) {
			return new Status(IStatus.ERROR, PDECore.PLUGIN_ID, PDECoreMessages.FeatureBasedExportOperation_ProblemDuringExport, e.getCause() != null ? e.getCause() : e);
		} finally {
			if (fInfo.zip) {
				deleteDir(new File(fBuildTempLocation));	
			}		
		}

	}

	protected void doExport(TransactionModel file, IProgressMonitor monitor) throws CoreException, InvocationTargetException {
		fHasErrors = false;

		SubMonitor subMonitor = SubMonitor.convert(monitor, Messages.TransactionExportJob_taskName, 5);

		// Generate sim_queues
		Message msg = null;
		byte[] iso = null;
		try {
			UseCase uc = Manager.get(IUseCaseManager.class).parse(file.getIFile());
			msg = uc.getAcquirer().getRequest().getMessage();
		} catch (ParserException e) {
			fHasErrors = true;
			PDECore.logException(e, "Error parsing msg (Json -> Msg), file: " + file.getIFile());
			msg = null;
		}
		if (msg != null) {
			try {
				iso = ParserUtils.formatMessage(msg);
				if (msg != null) {
					FileUtils.writeContents(file.getTgtFile(), ByteUtils.append(iso, (byte)10));
				}
			} catch (ParserException e) {
				fHasErrors = true;
				PDECore.logException(e, "Error formating Msg (Msg -> ISO 8583), Msg: " + msg.toJson());
			}
		}
		subMonitor.split(1);
		subMonitor.setTaskName(Messages.TransactionExportJob_runningBuildSimQueue);

	}
	protected void doExportSingleFile(File destination,TransactionModel[] files, IProgressMonitor monitor) throws CoreException, InvocationTargetException {
		fHasErrors = false;

		SubMonitor subMonitor = SubMonitor.convert(monitor, Messages.TransactionExportJob_taskName, 5);

		// Generate sim_queues
		Message msg = null;
		byte[] iso = null;
		if (files == null || files.length == 0) {
			return;
		}
		IUseCaseManager useCaseManager = Manager.get(IUseCaseManager.class);
		FileOutputStream writer = null;
		try {
			writer = new FileOutputStream(destination);
			for (TransactionModel file : files) {
				try {
					UseCase uc = useCaseManager.parse(file.getIFile());
					msg = uc.getAcquirer().getRequest().getMessage();
				} catch (ParserException e) {
					fHasErrors = true;
					PDECore.logException(e, "Error parsing msg (Json -> Msg), file: " + file.getIFile());
					msg = null;
				}
				if (msg != null) {
					try {
						iso = ParserUtils.formatMessage(msg);
						if (msg != null) {
							writer.write(ByteUtils.append(iso, (byte)10));
						}
					} catch (ParserException e) {
						fHasErrors = true;
						PDECore.logException(e, "Error formating Msg (Msg -> ISO 8583), Msg: " + msg.toJson());
					}
				}
			}
		} catch (Exception e) {
			Activator.getInstance().warning("Error exporting use cases to file "+files[0].getTgtFile());
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (Exception e) {}
			}
		}
		subMonitor.split(1);
		subMonitor.setTaskName(Messages.TransactionExportJob_runningBuildSimQueue);

	}
	protected void createDestination() throws InvocationTargetException {
		File file = new File(fInfo.destinationDirectory);
		if (!file.exists() || !file.isDirectory()) {
			if (!file.mkdirs())
				throw new InvocationTargetException(new Exception(Messages.ExportWizard_badDirectory));
		}
		if (fInfo.zip) {
			file = new File(fBuildTempLocation);
			if (!file.exists() || !file.isDirectory()) {
				if (!file.mkdirs())
					throw new InvocationTargetException(new Exception(Messages.ExportWizard_badDirectory));
			}
		}
	}

	protected void deleteDir(File dir) {
		if (dir.exists()) {
			if (dir.isDirectory()) {
				File[] children = dir.listFiles();
				if (children != null) {
					for (File element : children) {
						deleteDir(element);
					}
				}
			}
			dir.delete();
		}
	}

	public static void errorFound() {
		fHasErrors = true;
	}

	public boolean hasErrors() {
		return fHasErrors;
	}

	/**
	 * If we are exporting using the compiled classes from the workspace, this method will
	 * start an incremental build and test for build errors.  Returns a status explaining
	 * any errors found or Status.OK_STATUS.
	 * @param monitor progress monitor
	 * @return status explaining build errors or an OK status.
	 * @throws CoreException
	 */
	protected IStatus testBuildWorkspaceBeforeExport(IProgressMonitor monitor) throws CoreException {
		SubMonitor subMonitor = SubMonitor.convert(monitor, 50);
		subMonitor.split(5);
		return Status.OK_STATUS;

	}
	public void doConfiguration() {
		for (TransactionModel file : fInfo.getFiles()) {
			String isoFileName = file.getSrcFile().getName().substring(0,file.getSrcFile().getName().lastIndexOf('.')) +"."+ EXTENSION_SIM_QUEUE;
			file.setTgtFile(new File((fInfo.zip ? fBuildTempLocation : fInfo.destinationDirectory)+System.getProperty("file.separator")+isoFileName));
		}
	}
}
