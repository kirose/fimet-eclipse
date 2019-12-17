package com.fimet.editor.usecase.wizard.newuc;



import org.eclipse.swt.widgets.Label;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.ide.IIDEHelpContextIds;

import com.fimet.commons.messages.Messages;
import com.fimet.core.impl.swt.msg.IMessageContainer;
import com.fimet.core.impl.swt.msg.MessageViewer;
import com.fimet.core.net.ISocket;
import com.fimet.core.usecase.UseCase;


public class NewUseCaseWizardMsgPage extends WizardPage implements Listener, IMessageContainer {
	
	public static String DEFAULT_MTI = "0200";
	private UseCase useCase;
	private MessageViewer messageViewer;
	private NewUseCaseWizard wizard;
	
	public NewUseCaseWizardMsgPage(String pageName, NewUseCaseWizard wizard) {
		super(pageName);
		setPageComplete(true);
		this.wizard = wizard;
		this.useCase = wizard.getUseCase();
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
	    //createContainerControls(composite, nColumns);
		createIsoMsgControls(composite, nColumns);
		//createButtonsControls(composite, nColumns);
	    setControl(composite);

	    //hookListeners();
	    
		setErrorMessage(null);
		setMessage(null);
	    validateControls();
	    Dialog.applyDialogFont(composite);
	    PlatformUI.getWorkbench().getHelpSystem().setHelp(composite, "org.eclipse.jdt.ui.new_class_wizard_page_context");
	}

	protected void createIsoMsgControls(Composite parent, int nColumns) {
		
		GridLayout layout = new GridLayout(1,true);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		
		// top level group
		Composite composite = new Composite(parent, SWT.NONE);
		
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));//topLevel.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL | GridData.HORIZONTAL_ALIGN_FILL));
		composite.setFont(parent.getFont());
		
		Label lbl = new Label(composite,SWT.NONE);
		lbl.setText(Messages.NewUseCaseWizard_JsonMsg);
		lbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, nColumns, 1));
		
		messageViewer = new MessageViewer(this, true, composite, SWT.NONE);
//		messageViewer.setLayout(new FillLayout());
//		messageViewer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, nColumns, 1));
		
	}
	@Override
	public boolean isPageComplete() {
		return messageViewer.getMessage() != null;
	}

	/**
	 * The <code>WizardNewFileCreationPage</code> implementation of this
	 * <code>Listener</code> method handles all events and enablements for
	 * controls on this page. Subclasses may extend.
	 */
	@Override
	public void handleEvent(Event event) {
		setPageComplete(validateControls());
	}

	/**
	 * Returns whether this page's controls currently all contain valid values.
	 *
	 * @return <code>true</code> if all controls are valid, and
	 *         <code>false</code> if at least one is invalid
	 */
	protected boolean validateControls() {
		if (messageViewer.getMessage() != null) {
			setPageComplete(true);
			return true;
		}
		return false;
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible) {
			messageViewer.setFocus();
		}
	}
	@Override
	public void onModifyMessage() {
		useCase.getAcquirer().getRequest().setMessage(messageViewer.getMessage());
		setPageComplete(true);
	}

	@Override
	public ISocket getConnection() {
		return wizard.getMainPage().getAcquirer();
	}

	@Override
	public ISelectionProvider getSelectionProvider() {
		return null;
	}
}
