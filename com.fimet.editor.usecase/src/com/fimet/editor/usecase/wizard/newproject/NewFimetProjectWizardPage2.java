/*******************************************************************************
 * Copyright (c) 2000, 2018 IBM Corporation and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.fimet.editor.usecase.wizard.newproject;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.undo.CreateFileOperation;
import org.eclipse.ui.ide.undo.WorkspaceUndoUtil;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;

import com.fimet.commons.console.Console;
import com.fimet.commons.messages.Messages;
import com.fimet.commons.utils.StringUtils;
import com.fimet.core.ISO8583.parser.Message;
import com.fimet.core.impl.swt.AcquirerCombo;
import com.fimet.core.impl.swt.IssuerCombo;
import com.fimet.core.impl.swt.msg.IMessageContainer;
import com.fimet.core.impl.swt.msg.MessageViewer;
import com.fimet.core.net.ISocket;
import com.fimet.core.usecase.UseCase;


/**
 * The second page of the New Java project wizard. It allows to configure Use Cases.
 *
 * <p>
 * Clients may instantiate or subclass.
 * </p>
 *
 * @since 3.4
 */
public class NewFimetProjectWizardPage2 extends WizardPage {

	private final NewFimetProjectWizardPage1 fFirstPage;
	private UseCasesTableViewer table;
	private UseCase last;
	public static final String FOLDER_USE_CASES = "useCases"; 
	
	/**
	 * Constructor for the {@link NewFimetProjectWizardPage2}.
	 *
	 * @param mainPage the first page of the wizard
	 */
	public NewFimetProjectWizardPage2(NewFimetProjectWizardPage1 mainPage) {
		super("Use Cases");
		fFirstPage= mainPage;
		setTitle("Use Cases");
		setDescription("Create Use Cases");
	}
	@Override
	public void createControl(Composite parent) {
		
		GridLayout layout = new GridLayout(1,true);
        Composite composite = new Composite(parent, SWT.NONE);
		
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		composite.setFont(parent.getFont());
		
		createTableUsesCase(composite, 1);
		
	    setControl(composite);
	    Dialog.applyDialogFont(composite);
	    PlatformUI.getWorkbench().getHelpSystem().setHelp(composite, "org.eclipse.jdt.ui.new_class_wizard_page_context");
	}
	
	private void createTableUsesCase(Composite parent, int numColumns) {

		GridData gd;
		
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd.heightHint = 500;
		gd.widthHint = 350;
		table = new UseCasesTableViewer(this, parent, SWT.MULTI | SWT.H_SCROLL| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		table.getTable().getTable().setLayoutData(gd);
		
	}
	protected boolean validateControls() {
		setPageComplete(true);
		return true;
	}
	public List<UseCase> getUseCases(){
		return table.getEntities();
	}
	public void createUseCases(IPath folderUseCases, IProgressMonitor monitor) {
		List<UseCase> useCases = getUseCases();
		if (useCases == null || useCases.isEmpty()) {
			return;
		}
		for (UseCase useCase : useCases) {
			IPath newFilePath = folderUseCases.append(useCase.getName()+".uc");
			createNewFileUseCase(useCase, newFilePath, monitor);
		}
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
	public IFile createNewFileUseCase(UseCase useCase, IPath newFilePath, IProgressMonitor monitor) {
		if (useCase == null) {
			return null;
		}

		// create the new file and cache it if successful
		final IFile newFileHandle = createFileHandle(newFilePath);
		final InputStream initialContents = new ByteArrayInputStream(useCase.toJson().getBytes());

		CreateFileOperation op1 = new CreateFileOperation(newFileHandle,null, initialContents, Messages.FIMETProjectWizard_newUseCase);
		try {
			// see bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=219901
			// directly execute the operation so that the undo state is
			// not preserved.  Making this undoable resulted in too many
			// accidental file deletions.
			op1.execute(monitor, WorkspaceUndoUtil.getUIInfoAdapter(getShell()));
		} catch (final ExecutionException e) {
			getContainer().getShell().getDisplay().syncExec(() -> {
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
					IDEWorkbenchPlugin.log(getClass(),"createNewFileUseCase()", e.getCause()); //$NON-NLS-1$
					MessageDialog.openError(
						getContainer().getShell(),
						IDEWorkbenchMessages.WizardNewFileCreationPage_internalErrorTitle,
						NLS.bind(IDEWorkbenchMessages.WizardNewFileCreationPage_internalErrorMessage,
						e.getCause().getMessage())
					);
				}
			});
		}

		return newFileHandle;

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
	public void onNewUseCase() {
		UseCaseDialog dialog = new UseCaseDialog(
				null,
				last != null && last.getAcquirer() != null ? last.getAcquirer().getConnection() : null,
				last != null && last.getIssuer() != null ? last.getIssuer().getConnection() : null,
				getShell(), SWT.NONE);
		dialog.open();
		if (dialog.getOutput() != null) {
			table.add(last = dialog.getOutput());
		}
	}
	public void onEditUseCase() {
		if (table.getSelected() != null) {
			UseCaseDialog dialog = new UseCaseDialog(
					table.getSelected(),
					last != null && last.getAcquirer() != null ? last.getAcquirer().getConnection() : null,
					last != null && last.getIssuer() != null ? last.getIssuer().getConnection() : null,
					getShell(), SWT.NONE);
			dialog.open();
			if (dialog.getOutput() != null) {
				table.add(last = dialog.getOutput());
			}
		}
	}
	public void onDeleteUseCase() {
		if (table.getSelected() != null) {
			table.delete(table.getSelected());
		}
	}
}
