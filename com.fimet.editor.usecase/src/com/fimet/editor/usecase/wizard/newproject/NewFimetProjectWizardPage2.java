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
import org.eclipse.jface.text.TextEvent;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.undo.CreateFileOperation;
import org.eclipse.ui.ide.undo.WorkspaceUndoUtil;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;

import com.fimet.commons.console.Console;
import com.fimet.commons.converter.Converter;
import com.fimet.commons.exception.FimetException;
import com.fimet.commons.messages.Messages;
import com.fimet.commons.utils.StringUtils;
import com.fimet.core.IEnviromentManager;
import com.fimet.core.Manager;
import com.fimet.core.ISO8583.adapter.IAdapter;
import com.fimet.core.ISO8583.parser.IMessage;
import com.fimet.core.ISO8583.parser.Message;
import com.fimet.core.entity.sqlite.pojo.MessageIsoParameters;
import com.fimet.core.entity.sqlite.pojo.MessageIsoType;
import com.fimet.core.impl.swt.AcquirerCombo;
import com.fimet.core.impl.swt.IssuerCombo;
import com.fimet.core.impl.swt.VTextDocument;
import com.fimet.core.impl.swt.msgiso.MessageIsoSearchDialog;
import com.fimet.core.net.ISocket;
import com.fimet.core.usecase.UseCase;
import com.fimet.editor.json.utils.JsonUtils;
import com.fimet.editor.usecase.Activator;
import com.fimet.parser.util.ParserUtils;


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

	private static String defaultMessageLengthIndicator = "0200";
	private final NewFimetProjectWizardPage1 fFirstPage;
	private UseCasesTableViewer tvUseCases;
	private VTextDocument txtMsg;
	private AcquirerCombo cvAcquirer;
	private IssuerCombo cvIssuer;
	private Button btnRemove;
	private Button btnAdd;
	private Button btnSearch;
	private Button btnParse;
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
		
		GridLayout layout = new GridLayout();
        layout.numColumns = 1;
        layout.horizontalSpacing = 1;
        layout.verticalSpacing = 1;
        layout.marginHeight = 1;
        layout.marginWidth = 5;
        Composite composite = new Composite(parent, SWT.NONE);
		
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		composite.setFont(parent.getFont());
		
		createTableUsesCase(composite, 1);

		fillData();
		hookListeners();
		
	    setControl(composite);
	    Dialog.applyDialogFont(composite);
	    PlatformUI.getWorkbench().getHelpSystem().setHelp(composite, "org.eclipse.jdt.ui.new_class_wizard_page_context");
	}
	
	private void createTableUsesCase(Composite parent, int numColumns) {

		Label label;
		GridData gd;
		GridLayout layout = new GridLayout();
        layout.numColumns = numColumns;
        gd = new GridData(SWT.FILL, SWT.FILL, true, true,1,1);
        gd.heightHint = 200;
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(layout);
		composite.setLayoutData(gd);
		composite.setFont(parent.getFont());
		//composite.setBackground(new Color(Display.getDefault(), new RGB(0, 128, 128)));
		
		/*label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		label.setText("Use Cases:");*/
		
		gd = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd.heightHint = 200;
		gd.widthHint = 400;
		tvUseCases = new UseCasesTableViewer(composite, SWT.MULTI | SWT.H_SCROLL| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		tvUseCases.getTable().setLayoutData(gd);
		
		layout = new GridLayout();
        layout.numColumns = 2;
        gd = new GridData(SWT.FILL, SWT.FILL, true, true,numColumns,1);
        gd.heightHint = 50;
		composite = new Composite(parent, SWT.NONE);
		composite.setLayout(layout);
		composite.setLayoutData(gd);
		composite.setFont(parent.getFont());
		//composite.setBackground(new Color(Display.getDefault(), new RGB(0, 128, 0)));
		
		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		label.setText("Acquirer:");
		
        cvAcquirer = new AcquirerCombo(composite); 
        cvAcquirer.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        cvAcquirer.getCombo().addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(cvAcquirer.getStructuredSelection() != null && cvAcquirer.getStructuredSelection().getFirstElement() != null) {
					cvAcquirer.valid();
					btnParse.setEnabled(true);
					btnSearch.setEnabled(true);
				} else {
					cvAcquirer.invalid();
				}
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		
		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		label.setText("Issuer:");
		
        cvIssuer = new IssuerCombo(composite);
        cvIssuer.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        
		layout = new GridLayout();
        layout.numColumns = 1;
        gd = new GridData(SWT.FILL, SWT.FILL, true, true,numColumns,1);
        gd.heightHint = 110;
		composite = new Composite(parent, SWT.NONE);
		composite.setLayout(layout);
		composite.setLayoutData(gd);
		composite.setFont(parent.getFont());
		//composite.setBackground(new Color(Display.getDefault(), new RGB(128, 0, 0)));
		
		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		label.setText(Messages.NewUseCaseWizard_Msg);
		
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd.heightHint = 110;
		txtMsg = new VTextDocument(composite, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		txtMsg.getControl().setLayoutData(gd);
		txtMsg.valid();
		JsonUtils.reconcilerJson(txtMsg, null);
        
		layout = new GridLayout();
        layout.numColumns = 6;
        layout.makeColumnsEqualWidth = true;
        gd = new GridData(SWT.FILL, SWT.CENTER, true, false, numColumns, 1);
        gd.heightHint = 35;
		composite = new Composite(parent, SWT.NONE);
		composite.setLayout(layout);
		composite.setLayoutData(gd);
		composite.setFont(parent.getFont());
		//composite.setBackground(new Color(Display.getDefault(), new RGB(0, 0, 128)));
        
		//new Composite(composite, SWT.NONE).setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		//new Composite(composite, SWT.NONE).setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(composite, SWT.NONE).setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(composite, SWT.NONE).setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		btnSearch = new Button(composite, SWT.NONE);
		btnSearch.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnSearch.setText("Search");
		btnSearch.setEnabled(false);
		
		btnParse = new Button(composite, SWT.NONE);
		btnParse.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnParse.setText("Parse");
		btnParse.setEnabled(false);
		
		btnAdd = new Button(composite, SWT.NONE);
		btnAdd.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnAdd.setText("Add");
		btnAdd.setEnabled(false);

		btnRemove = new Button(composite, SWT.NONE);
		btnRemove.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnRemove.setText("Remove");
		btnRemove.setEnabled(false);
		
	}
	protected boolean validateControls() {
		if (txtMsg.isValid()) {
			setPageComplete(true);
			return true;
		}
		return false;
	}
	private void fillData() {
	}
	private void hookListeners() {
		txtMsg.addTextListener((TextEvent e)->{
			if (txtMsg.isValid() && cvAcquirer.getSelected() != null) {
			}
		});
		tvUseCases.getTable().addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Table table = (Table)e.widget;
				if (table.getSelection() != null && table.getSelection().length > 0) {
					btnRemove.setEnabled(true);
				} else {
					btnRemove.setEnabled(false);
				}
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		
		cvAcquirer.getCombo().addSelectionListener(new SelectionAdapter() {
        	public void widgetSelected(SelectionEvent e) {
        		if (!cvAcquirer.getSelection().isEmpty() && !cvIssuer.getSelection().isEmpty()) {
        			btnAdd.setEnabled(true);
        		} else {
        			btnAdd.setEnabled(false);
        		}
        	}
        });
		cvIssuer.getCombo().addSelectionListener(new SelectionAdapter() {
        	public void widgetSelected(SelectionEvent e) {
        		if (!cvAcquirer.getSelection().isEmpty() && !cvIssuer.getSelection().isEmpty()) {
        			btnAdd.setEnabled(true);
        		} else {
        			btnAdd.setEnabled(false);
        		}
        	}
        });
		btnParse.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (cvAcquirer.getSelected() != null) {
					onParse();
				}
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		btnSearch.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (cvAcquirer.getSelected() != null) {
					onSearch();
				}
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		btnAdd.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ISocket acquirer = cvAcquirer.getSelected();
				ISocket issuer = cvIssuer.getSelected();
				if (acquirer != null && issuer != null) {
					UseCase useCase = new UseCase(acquirer, issuer);
					if (txtMsg.getDocument().get() != null && txtMsg.getDocument().get().trim().length() > 0) {
						try {
							useCase.getAcquirer().getRequest().setMessage(ParserUtils.parseJsonMessage(txtMsg.getDocument().get()));
							useCase.getAcquirer().getRequest().getMessage().setAdapter((IAdapter)cvAcquirer.getSelected().getAdapter());
							tvUseCases.addUseCase(useCase);
						} catch (Exception ex) {
							Console.getInstance().warning(NewFimetProjectWizardPage2.class, "Error parsing json message");
							Activator.getInstance().warning("Error parsing json message", ex);
						}
						//MessageTemplates.createTemplate(useCase.getAcquirer().getRequest().getMessage(), acquirer.getParser().toString());						
					} else {
						Console.getInstance().warning(NewFimetProjectWizardPage2.class, "The message is null");
					}
				}
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		btnRemove.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (tvUseCases.getTable().getSelection() != null && tvUseCases.getTable().getSelection().length > 0 ) {
					TableItem[] selection = tvUseCases.getTable().getSelection();
					tvUseCases.remove(((UseCase)selection[0].getData()));
				}
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		/*monitor = new EnviromentMonitor() {
			@Override
			public void onDisconnected(DataBase db) {
				cleanData();
			}
			@Override
			public void onConnected(DataBase db) {
				fillData();
			}
		};
		FtpManager.getInstance().addConnectionMonitor(monitor);
		getWizard().getContainer().getShell().addListener(SWT.Close, new Listener() {
			@Override
			public void handleEvent(Event event) {
				FtpManager.getInstance().removeConnectionMonitor(monitor);
			}
		});*/
	}
	public List<UseCase> getUseCases(){
		return tvUseCases.getUseCases();
	}
	public void createUseCases(IPath folderUseCases, IProgressMonitor monitor) {
		List<UseCase> useCases = getUseCases();
		if (useCases == null || useCases.isEmpty()) {
			return;
		}
		int padLength = (useCases.size() + "").length();
		int index = 1;
		for (UseCase useCase : useCases) {
			String number = StringUtils.leftPad((index++)+"", padLength, '0');
			IPath newFilePath = folderUseCases.append("UC"+number+".uc");
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
	public void onParse() {
		ISocket socket = cvAcquirer.getSelected();
		if (socket == null || socket.getParser() == null) {
			throw new FimetException("Select an Acquirer");
		}
		com.fimet.core.impl.swt.msg.MessageParseDialog dialog = new com.fimet.core.impl.swt.msg.MessageParseDialog(getShell(),socket.getParser(), SWT.NONE);
		dialog.open();
		Message msg = dialog.getMessage();
		if (msg != null) {
			try {
				txtMsg.getDocument().set(msg.toJson());
			} catch (Exception ex) {
				Activator.getInstance().error("Error parsing message", ex);
			}					
		}
	}
	public void onSearch() {
		try {
			MessageIsoParameters params = new MessageIsoParameters();
			if (Manager.get(IEnviromentManager.class).getActive() != null) {
				params.setIdTypeEnviroment(Manager.get(IEnviromentManager.class).getActive().getIdType());
			}
			if (cvAcquirer.getSelected() != null) {
				ISocket iap = cvAcquirer.getSelected();
				if (iap != null && iap.getParser() != null) {
					params.setIdParser(iap.getParser().getId());
				}
			}
			params.setType(MessageIsoType.ACQ_REQ.getId());
			MessageIsoSearchDialog dialog = new MessageIsoSearchDialog(params, PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), SWT.NONE);
			dialog.open();
			if (dialog.getMessage() != null) {
				byte[] isoAndMli = Converter.hexToAscii(dialog.getMessage().getMessage().getBytes());
				IMessage msg = ParserUtils.parseMessage(isoAndMli, dialog.getMessage().getIdParser());
				txtMsg.getDocument().set(msg.toJson());
			}
		} catch (Exception e) {
			Activator.getInstance().error("Error searching message", e);
		}
	}
}
