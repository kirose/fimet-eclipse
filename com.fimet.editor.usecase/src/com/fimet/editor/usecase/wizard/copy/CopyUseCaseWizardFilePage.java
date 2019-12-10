package com.fimet.editor.usecase.wizard.copy;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceStatus;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.swt.widgets.Label;
import org.eclipse.jdt.internal.ui.JavaPlugin;
import org.eclipse.jdt.internal.ui.wizards.TypedElementSelectionValidator;
import org.eclipse.jdt.internal.ui.wizards.TypedViewerFilter;
import org.eclipse.jdt.internal.ui.wizards.dialogfields.DialogField;
import org.eclipse.jdt.internal.ui.wizards.dialogfields.IDialogFieldListener;
import org.eclipse.jdt.internal.ui.wizards.dialogfields.IStringButtonAdapter;
import org.eclipse.jdt.internal.ui.wizards.dialogfields.LayoutUtil;
import org.eclipse.jdt.internal.ui.wizards.dialogfields.StringButtonDialogField;
import org.eclipse.jdt.ui.JavaElementComparator;
import org.eclipse.jdt.ui.JavaElementLabelProvider;
import org.eclipse.jdt.ui.StandardJavaElementContentProvider;
import org.eclipse.jdt.ui.wizards.NewContainerWizardPage;
import org.eclipse.jdt.ui.wizards.NewTypeWizardPage;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.util.BidiUtils;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.ide.undo.CreateFileOperation;
import org.eclipse.ui.ide.undo.WorkspaceUndoUtil;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.internal.ide.IIDEHelpContextIds;
import org.eclipse.ui.internal.ide.dialogs.CreateLinkedResourceGroup;

import com.fimet.commons.io.FileUtils;
import com.fimet.commons.messages.Messages;
import com.fimet.commons.preference.IPreference;
import com.fimet.core.IUseCaseManager;
import com.fimet.core.Manager;
import com.fimet.core.ISO8583.parser.Field;
import com.fimet.core.ISO8583.parser.Message;
import com.fimet.core.impl.swt.AcquirerCombo;
import com.fimet.core.impl.swt.IssuerCombo;
import com.fimet.core.impl.swt.VCombo;
import com.fimet.core.impl.swt.VText;
import com.fimet.core.net.ISocket;
import com.fimet.core.usecase.Acquirer;
import com.fimet.core.usecase.Issuer;
import com.fimet.core.usecase.UseCase;
import com.fimet.parser.util.ParserUtils;

import org.eclipse.jdt.internal.ui.wizards.dialogfields.Separator;
import org.eclipse.jdt.internal.ui.dialogs.StatusInfo;
import org.eclipse.jdt.internal.ui.dialogs.StatusUtil;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class CopyUseCaseWizardFilePage extends WizardPage implements Listener {
	
	private static final int SIZING_CONTAINER_GROUP_HEIGHT = 250;

	// the current resource selection
	private IStructuredSelection currentSelection;

	private URI linkTargetPath;

	// widgets
	private ResourceAndContainerGroupCopy resourceGroup;

	// initial value stores
	private String initialFileName;

	/**
	 * The file extension to use for this page's file name field when it does
	 * not exist yet.
	 *
	 * @see WizardNewFileCreationPage#setFileExtension(String)
	 * @since 3.3
	 */
	private String initialFileExtension;

	private IPath initialContainerFullPath;

	private boolean initialAllowExistingResources = true;


	private CopyUseCaseWizard wizard;
	private CopyUseCaseWizardFieldsPage secondPage;
	private IUseCaseManager useCaseManager = Manager.get(IUseCaseManager.class);
	//private VText txtMsg;
	
	public CopyUseCaseWizardFilePage(String pageName, CopyUseCaseWizard wizard) {
		super(pageName);
		setPageComplete(false);
		this.wizard = wizard;
		this.currentSelection = wizard.getSelection();
		this.setFileExtension(Messages.NewTransactionWizard_UseCaseFileExtension);
	}

	@Override
	public void createControl(Composite parent) {
	    initializeDialogUnits(parent);
	    
	    Composite composite = new Composite(parent, SWT.NONE);
	    composite.setFont(parent.getFont());
	    int nColumns = 1;
	    GridLayout layout = new GridLayout();
	    layout.numColumns = nColumns;
	    composite.setLayout(layout);
	    composite.setFont(parent.getFont());
	    composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		PlatformUI.getWorkbench().getHelpSystem().setHelp(composite, IIDEHelpContextIds.NEW_FILE_WIZARD_PAGE);

	    createTransactionNameControls(composite, nColumns);
	    //createSeparator(composite, nColumns);
	    //createFileNameControls(composite, nColumns);
	    setControl(composite);
	    Dialog.applyDialogFont(composite);
	    PlatformUI.getWorkbench().getHelpSystem().setHelp(composite, "org.eclipse.jdt.ui.new_class_wizard_page_context");
	    configureFileName(wizard.getUseCaseResources());
	}


	private void configureFileName(List<IResource> useCasesSrc) {
		if (useCasesSrc == null || useCasesSrc.isEmpty()) {
			resourceGroup.setResource("");
			return;
		}
		
		StringBuilder sb = new StringBuilder();
		for (IResource useCaseRes : useCasesSrc) {
			String fileName = useCaseRes.getLocation().toString().replace('\\', '/');
			fileName = fileName.substring(fileName.lastIndexOf('/')+1);
			sb.append(fileName).append('\n');
		}
		sb.delete(sb.length()-1, sb.length());
		resourceGroup.setResource(sb.toString());
	}

	protected void createTransactionNameControls(Composite composite, int nColumns) {
		// top level group
		Composite topLevel = new Composite(composite, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = nColumns;
		topLevel.setLayout(layout);
		topLevel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));//topLevel.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL | GridData.HORIZONTAL_ALIGN_FILL));
		topLevel.setFont(composite.getFont());

		// resource and container group
		resourceGroup = new ResourceAndContainerGroupCopy(
			topLevel,
			this,
			getNewFileLabel(),
			"file",
			false,
			SIZING_CONTAINER_GROUP_HEIGHT,
			wizard.getUseCaseResources()
		);
		resourceGroup.setAllowExistingResources(initialAllowExistingResources);
		initialPopulateContainerNameField();
		
		if (initialFileName != null) {
			resourceGroup.setResource(initialFileName);
		}
		if (initialFileExtension != null) {
			resourceGroup.setResourceExtension(initialFileExtension);
		}
		
		// Show description on opening
		setErrorMessage(null);
		setMessage(null);
		validatePage();
		setControl(topLevel);
	}
	@Override
	public boolean isPageComplete() {
		return resourceGroup.isValid();
	}
	@Override
	public boolean canFlipToNextPage() {
		return true;
	}
	private boolean validateControls() {
		if (resourceGroup != null && resourceGroup.isValid()) {
			setPageComplete(true);
		}
		return false;
	}
	/**
	 * Creates the widget for advanced options.
	 *
	 * @param parent
	 *            the parent composite
	 */
	protected void createSeparator(Composite composite, int nColumns) {
	  new Separator(258).doFillIntoGrid(composite, nColumns, convertHeightInCharsToPixels(1));
  	}
	
	public class ResourceAndContainerGroupCopy extends ResourceAndContainerGroup {
		private boolean isValid;
		public ResourceAndContainerGroupCopy(Composite parent, Listener client, String resourceFieldLabel, String resourceType, boolean showClosedProjects, int heightHint,List<IResource> resources) {
			super(parent, client, resourceFieldLabel, resourceType, showClosedProjects, heightHint,resources);
		}
		public void valid(boolean valid) {
			isValid = valid;
		}
		public void valid() {
			isValid = true;
		}
		public void invalid() {
			isValid = false;
		}
		public boolean isValid() {
	    	return isValid;
	    }
		@Override
		public boolean validateControls() {
			isValid = super.validateControls();
			CopyUseCaseWizardFilePage.this.validateControls();
			return isValid;
		}
	}
	
	
	/**
	 * Creates a file resource given the file handle and contents.
	 *
	 * @param fileHandle
	 *            the file handle to create a file resource with
	 * @param contents
	 *            the initial contents of the new file resource, or
	 *            <code>null</code> if none (equivalent to an empty stream)
	 * @param monitor
	 *            the progress monitor to show visual progress with
	 * @exception CoreException
	 *                if the operation fails
	 * @exception OperationCanceledException
	 *                if the operation is canceled
	 *
	 * @deprecated As of 3.3, use or override {@link #createNewFile()} which
	 *             uses the undoable operation support. To supply customized
	 *             file content for a subclass, use
	 *             {@link #getInitialContents()}.
	 */
	@Deprecated
	protected void createFile(IFile fileHandle, InputStream contents,
			IProgressMonitor monitor) throws CoreException {
		SubMonitor subMonitor = SubMonitor.convert(monitor, 100);
		if (contents == null) {
			contents = new ByteArrayInputStream(new byte[0]);
		}

		try {
			// Create a new file resource in the workspace
			if (linkTargetPath != null) {
				fileHandle.createLink(linkTargetPath, IResource.ALLOW_MISSING_LOCAL, subMonitor.split(100));
			} else {
				IPath path = fileHandle.getFullPath();
				IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
				int numSegments = path.segmentCount();
				if (numSegments > 2 && !root.getFolder(path.removeLastSegments(1)).exists()) {
					// If the direct parent of the path doesn't exist, try to
					// create the necessary directories.
					SubMonitor loopMonitor = subMonitor.split(30);
					for (int i = numSegments - 2; i > 0; i--) {
						loopMonitor.setWorkRemaining(i);
						IFolder folder = root.getFolder(path
								.removeLastSegments(i));
						if (!folder.exists()) {
							folder.create(false, true, loopMonitor.split(1));
						}
					}
				}
				subMonitor.setWorkRemaining(100);
				fileHandle.create(contents, false, subMonitor.split(100));
			}
		} catch (CoreException e) {
			// If the file already existed locally, just refresh to get contents
			if (e.getStatus().getCode() == IResourceStatus.PATH_OCCUPIED) {
				fileHandle.refreshLocal(IResource.DEPTH_ZERO, null);
			} else {
				throw e;
			}
		}
	}

	/**
	 * Creates a file resource handle for the file with the given workspace
	 * path. This method does not create the file resource; this is the
	 * responsibility of <code>createFile</code>.
	 *
	 * @param filePath
	 *            the path of the file resource to create a handle for
	 * @return the new file resource handle
	 * @see #createFile
	 */
	protected IFile createFileHandle(IPath filePath) {
		return IDEWorkbenchPlugin.getPluginWorkspace().getRoot().getFile(
				filePath);
	}
	/**
	 * Creates a new file resource in the selected container and with the
	 * selected name. Creates any missing resource containers along the path;
	 * does nothing if the container resources already exist.
	 * <p>
	 * In normal usage, this method is invoked after the user has pressed Finish
	 * on the wizard; the enablement of the Finish button implies that all
	 * controls on on this page currently contain valid values.
	 * </p>
	 * <p>
	 * Note that this page caches the new file once it has been successfully
	 * created; subsequent invocations of this method will answer the same file
	 * resource without attempting to create it again.
	 * </p>
	 * <p>
	 * This method should be called within a workspace modify operation since it
	 * creates resources.
	 * </p>
	 *
	 * @return the created file resource, or <code>null</code> if the file was
	 *         not created
	 */
	public IFile createNewFile(IResource useCaseRes, String nameFile) {

		// create the new file and cache it if successful

		final IPath containerPath = resourceGroup.getContainerFullPath();
		IPath newFilePath = containerPath.append(nameFile);
		IResource resource = ResourcesPlugin.getWorkspace().getRoot().findMember(newFilePath);

		if (resource != null) {
			UseCase useCase = useCaseManager.parse(useCaseRes);
			final IFile newFileHandle = createFileHandle(newFilePath);
			replaceFields(useCase);
			if (useCase == null) {
				return null;
			}
			File file = new java.io.File(resource.getLocation().toString());
			//ResourcesPlugin.getWorkspace().delete(new IResource[] {resource}, true, new NullProgressMonitor());
			FileUtils.writeContents(file, useCase.toJson());
			return newFileHandle;
		} else {
			UseCase useCase = useCaseManager.parse(useCaseRes);
			final IFile newFileHandle = createFileHandle(newFilePath);
			replaceFields(useCase);
			if (useCase == null) {
				return null;
			}
			final InputStream initialContents = new ByteArrayInputStream(useCase.toJson().getBytes());
	
			IRunnableWithProgress op = monitor -> {
				CreateFileOperation op1 = new CreateFileOperation(newFileHandle,
						linkTargetPath, initialContents,
						IDEWorkbenchMessages.WizardNewFileCreationPage_title);
				try {
					// see bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=219901
					// directly execute the operation so that the undo state is
					// not preserved.  Making this undoable resulted in too many
					// accidental file deletions.
					op1.execute(monitor, WorkspaceUndoUtil
							.getUIInfoAdapter(getShell()));
				} catch (final ExecutionException e) {
					getContainer().getShell().getDisplay().syncExec(
							() -> {
								if (e.getCause() instanceof CoreException) {
									ErrorDialog
											.openError(
													getContainer()
															.getShell(), // Was
													// Utilities.getFocusShell()
													IDEWorkbenchMessages.WizardNewFileCreationPage_errorTitle,
													null, // no special
													// message
													((CoreException) e
															.getCause())
															.getStatus());
								} else {
									IDEWorkbenchPlugin.log(getClass(),"createNewFile()", e.getCause()); //$NON-NLS-1$
									MessageDialog.openError(
										getContainer().getShell(),
										IDEWorkbenchMessages.WizardNewFileCreationPage_internalErrorTitle,
										NLS.bind(IDEWorkbenchMessages.WizardNewFileCreationPage_internalErrorMessage,
										e.getCause().getMessage())
									);
								}
							});
				}
			};
			try {
				getContainer().run(true, true, op);
			} catch (InterruptedException e) {
				return null;
			} catch (InvocationTargetException e) {
				// Execution Exceptions are handled above but we may still get
				// unexpected runtime errors.
				IDEWorkbenchPlugin.log(getClass(),
						"createNewFile()", e.getTargetException()); //$NON-NLS-1$
				MessageDialog
						.open(MessageDialog.ERROR,
								getContainer().getShell(),
								IDEWorkbenchMessages.WizardNewFileCreationPage_internalErrorTitle,
								NLS
										.bind(
												IDEWorkbenchMessages.WizardNewFileCreationPage_internalErrorMessage,
												e.getTargetException().getMessage()), SWT.SHEET);
	
				return null;
			}
			return newFileHandle;
		}
	}

	private void replaceFields(UseCase useCase) {
		ISocket acquirer = secondPage.getAcquirer();
		if (acquirer != null) {
			if (useCase.getAcquirer() == null) {
				useCase.setAcquirer(new Acquirer());
			}
			useCase.getAcquirer().setConnection(acquirer);
		}
		ISocket issuer = secondPage.getIssuer();
		if (issuer != null) {
			if (useCase.getIssuer() == null) {
				useCase.setIssuer(new Issuer());
			}
			useCase.getIssuer().setConnection(issuer);
		}
		if (useCase.getAcquirer() != null && useCase.getAcquirer().getRequest() != null && useCase.getAcquirer().getRequest().getMessage() != null) {
			if (secondPage.getMti() != null && secondPage.getMti().length() > 0) {
				useCase.getAcquirer().getRequest().getMessage().setMti(secondPage.getMti());
			}
			Map<String, String> fields = secondPage.getFieldsToReplace();
			Message msg = useCase.getAcquirer().getRequest().getMessage();
			for (Map.Entry<String, String> e : fields.entrySet()) {
				if (msg.hasField(e.getKey())) {
					msg.setValue(e.getKey(), e.getValue());
				}

			}
		}
	}

	/**
	 * Returns the scheduling rule to use when creating the resource at the
	 * given container path. The rule should be the creation rule for the
	 * top-most non-existing parent.
	 *
	 * @param resource
	 *            The resource being created
	 * @return The scheduling rule for creating the given resource
	 * @since 3.1
	 * @deprecated As of 3.3, scheduling rules are provided by the undoable
	 *             operation that this page creates and executes.
	 */
	@Deprecated
	protected ISchedulingRule createRule(IResource resource) {
		IResource parent = resource.getParent();
		while (parent != null) {
			if (parent.exists()) {
				return resource.getWorkspace().getRuleFactory().createRule(
						resource);
			}
			resource = parent;
			parent = parent.getParent();
		}
		return resource.getWorkspace().getRoot();
	}

	/**
	 * Returns the current full path of the containing resource as entered or
	 * selected by the user, or its anticipated initial value.
	 *
	 * @return the container's full path, anticipated initial value, or
	 *         <code>null</code> if no path is known
	 */
	public IPath getContainerFullPath() {
		return resourceGroup.getContainerFullPath();
	}

	/**
	 * Returns the current file name as entered by the user, or its anticipated
	 * initial value. <br>
	 * <br>
	 * The current file name will include the file extension if the
	 * preconditions are met.
	 *
	 * @see WizardNewFileCreationPage#setFileExtension(String)
	 *
	 * @return the file name, its anticipated initial value, or
	 *         <code>null</code> if no file name is known
	 */
	public String getFileName() {
		if (resourceGroup == null) {
			return initialFileName;
		}

		return resourceGroup.getResource();
	}

	/**
	 * Returns the file extension to use when creating the new file.
	 *
	 * @return the file extension or <code>null</code>.
	 * @see WizardNewFileCreationPage#setFileExtension(String)
	 * @since 3.3
	 */
	public String getFileExtension() {
		if (resourceGroup == null) {
			return initialFileExtension;
		}
		return resourceGroup.getResourceExtension();
	}

	/**
	 * Returns the label to display in the file name specification visual
	 * component group.
	 * <p>
	 * Subclasses may reimplement.
	 * </p>
	 *
	 * @return the label to display in the file name specification visual
	 *         component group
	 */
	protected String getNewFileLabel() {
		return "Files Names:";
	}

	/**
	 * The <code>WizardNewFileCreationPage</code> implementation of this
	 * <code>Listener</code> method handles all events and enablements for
	 * controls on this page. Subclasses may extend.
	 */
	@Override
	public void handleEvent(Event event) {
		setPageComplete(validatePage());
	}

	/**
	 * Sets the initial contents of the container name entry field, based upon
	 * either a previously-specified initial value or the ability to determine
	 * such a value.
	 */
	protected void initialPopulateContainerNameField() {
		if (initialContainerFullPath != null) {
			resourceGroup.setContainerFullPath(initialContainerFullPath);
		} else {
			Iterator it = currentSelection.iterator();
			if (it.hasNext()) {
				Object object = it.next();
				IResource selectedResource = Adapters.adapt(object, IResource.class);
				if (selectedResource != null) {
					if (selectedResource.getType() == IResource.FILE) {
						selectedResource = selectedResource.getParent();
					}
					if (selectedResource.isAccessible()) {
						resourceGroup.setContainerFullPath(selectedResource
								.getFullPath());
					}
				}
			}
		}
	}

	/**
	 * Sets the flag indicating whether existing resources are permitted to be
	 * specified on this page.
	 *
	 * @param value
	 *            <code>true</code> if existing resources are permitted, and
	 *            <code>false</code> otherwise
	 * @since 3.4
	 */
	public void setAllowExistingResources(boolean value) {
		if (resourceGroup == null) {
			initialAllowExistingResources = value;
		} else {
			resourceGroup.setAllowExistingResources(value);
		}
	}

	/**
	 * Sets the value of this page's container name field, or stores it for
	 * future use if this page's controls do not exist yet.
	 *
	 * @param path
	 *            the full path to the container
	 */
	public void setContainerFullPath(IPath path) {
		if (resourceGroup == null) {
			initialContainerFullPath = path;
		} else {
			resourceGroup.setContainerFullPath(path);
		}
	}

	/**
	 * Sets the value of this page's file name field, or stores it for future
	 * use if this page's controls do not exist yet.
	 *
	 * @param value
	 *            new file name
	 */
	public void setFileName(String value) {
		if (resourceGroup == null) {
			initialFileName = value;
		} else {
			resourceGroup.setResource(value);
		}
	}

	/**
	 * Set the only file extension allowed for this page's file name field. If
	 * this page's controls do not exist yet, store it for future use. <br>
	 * <br>
	 * If a file extension is specified, then it will always be appended with a
	 * '.' to the text from the file name field for validation when the
	 * following conditions are met: <br>
	 * <br>
	 * (1) File extension length is greater than 0 <br>
	 * (2) File name field text length is greater than 0 <br>
	 * (3) File name field text does not already end with a '.' and the file
	 * extension specified (case sensitive) <br>
	 * <br>
	 * The file extension will not be reflected in the actual file name field
	 * until the file name field loses focus.
	 *
	 * @param value
	 *            The file extension without the '.' prefix (e.g. 'java', 'xml')
	 * @since 3.3
	 */
	public void setFileExtension(String value) {
		if (resourceGroup == null) {
			initialFileExtension = value;
		} else {
			resourceGroup.setResourceExtension(value);
		}
	}


	/**
	 * Returns whether this page's controls currently all contain valid values.
	 *
	 * @return <code>true</code> if all controls are valid, and
	 *         <code>false</code> if at least one is invalid
	 */
	protected boolean validatePage() {
		boolean valid = true;

		if (!resourceGroup.areAllValuesValid()) {
			// if blank name then fail silently
			if (resourceGroup.getProblemType() == ResourceAndContainerGroup.PROBLEM_RESOURCE_EMPTY
					|| resourceGroup.getProblemType() == ResourceAndContainerGroup.PROBLEM_CONTAINER_EMPTY) {
				setMessage(resourceGroup.getProblemMessage());
				setErrorMessage(null);
			} else {
				setErrorMessage(resourceGroup.getProblemMessage());
			}
			valid = false;
		}

		String resourceName = resourceGroup.getResource();
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IStatus result = workspace.validateName(resourceName, IResource.FILE);
		if (!result.isOK()) {
			setErrorMessage(result.getMessage());
			return false;
		}

		// validateLinkedResource sets messages itself
		if (valid) {
			setMessage(null);
			setErrorMessage(null);

			// perform "resource exists" check if it was skipped in
			// ResourceAndContainerGroup
			if (resourceGroup.getAllowExistingResources()) {
				String problemMessage = NLS.bind(
						IDEWorkbenchMessages.ResourceGroup_nameExists,
						getFileName());
				IPath resourcePath = getContainerFullPath().append(
						getFileName());
				if (workspace.getRoot().getFolder(resourcePath).exists()) {
					setErrorMessage(problemMessage);
					valid = false;
				}
				if (workspace.getRoot().getFile(resourcePath).exists()) {
					setMessage(problemMessage, IMessageProvider.WARNING);
				}
			}
		}
		if (isFilteredByParent()) {
			setMessage(IDEWorkbenchMessages.WizardNewFileCreationPage_resourceWillBeFilteredWarning, IMessageProvider.ERROR);
			valid = false;
		}
		if (getErrorMessage() == null) {
			setPageComplete(true);
		}
		return valid;
	}

	private boolean isFilteredByParent() {
		IPath containerPath = resourceGroup.getContainerFullPath();
		if (containerPath == null)
			return false;
		String resourceName = resourceGroup.getResource();
		if (resourceName == null)
			return false;
		if (resourceName.length() > 0) {
			IPath newFilePath = containerPath.append(resourceName);
			if (newFilePath.segmentCount() < 2)
				return false;
			IFile newFileHandle = createFileHandle(newFilePath);
			IWorkspace workspace = newFileHandle.getWorkspace();
			return !workspace.validateFiltered(newFileHandle).isOK();
		}
		return false;
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible) {
			resourceGroup.setFocus();
		}
	}
	public void setSecondPage(CopyUseCaseWizardFieldsPage secondPage) {
		this.secondPage = secondPage;
	}
	public List<String> getResources(){
		return resourceGroup.getResources();
	}
	public IPath getContainerPath() {
		return resourceGroup.getContainerFullPath();
	}	
}
