package com.fimet.editor.usecase.wizard.copy;


import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.widgets.Label;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.ide.IIDEHelpContextIds;

import com.fimet.commons.messages.Messages;
import com.fimet.core.impl.swt.AcquirerCombo;
import com.fimet.core.impl.swt.IssuerCombo;
import com.fimet.core.impl.swt.VCombo;
import com.fimet.core.impl.swt.VText;
import com.fimet.core.net.ISocket;

import org.eclipse.jdt.internal.ui.wizards.dialogfields.Separator;
import org.eclipse.jdt.internal.ui.dialogs.StatusInfo;
import org.eclipse.jdt.internal.ui.dialogs.StatusUtil;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class CopyUseCaseWizardFieldsPage extends WizardPage implements Listener {
	

	private static final int SIZING_CONTAINER_GROUP_HEIGHT = 250;
	private VText txtMti;
	private VCombo cboAcquirer;
	private VCombo cboIssuer;
	private FieldsTableViewer tblFields;
	private Button btnAdd;
	private Button btnDelete;
	private CopyUseCaseWizard wizard;
	private Map<String,String> fieldsToReplace;	
	public CopyUseCaseWizardFieldsPage(String pageName, CopyUseCaseWizard wizard) {
		super(pageName);
		setPageComplete(true);
		this.wizard = wizard;
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
		createIapAcquirerControls(composite, nColumns);
		createIapIssuerControls(composite, nColumns);
		createMtiControls(composite, nColumns);
		createTableFieldsControl(composite, nColumns);
		//createIsoMsgControls(composite, nColumns);
	    setControl(composite);

	    //hookListeners();
	    
		setErrorMessage(null);
		setMessage(null);
	    validateControls();
	    Dialog.applyDialogFont(composite);
	    PlatformUI.getWorkbench().getHelpSystem().setHelp(composite, "org.eclipse.jdt.ui.new_class_wizard_page_context");
	    hookListeners();
	}
	private void createTableFieldsControl(Composite parent, int nColumns) {
		
		
		Label lbl = new Label(parent,SWT.NONE);
		lbl.setText("Fields to replace in Message (if exists):");
		lbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		GridLayout layout = new GridLayout(1,true);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setFont(parent.getFont());
		composite.setLayout(layout);
		GridData gridData = new GridData(SWT.FILL, SWT.CENTER, true, false, nColumns, 1);
		gridData.heightHint = 300;
		composite.setLayoutData(gridData);
		tblFields = new FieldsTableViewer(composite, SWT.MULTI | SWT.H_SCROLL| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		//tblFields.add(new FieldsTableViewer.Field("3","000000"));
		tblFields.add(new FieldsTableViewer.Field("2","4000000000000002"));
		tblFields.add(new FieldsTableViewer.Field("35","4000000000000002D2210"));
		tblFields.add(new FieldsTableViewer.Field("63.EZ.10.track2","4000000000000002D22102210000064201001F"));
		tblFields.getTable().select(0);
		
		layout = new GridLayout(4,true);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		composite = new Composite(parent, SWT.NONE);
		composite.setFont(parent.getFont());
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, nColumns, 1));
		
		new Label(composite,SWT.NONE).setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 2, 1));;
		
		btnAdd = new Button(composite, SWT.NONE);
		btnAdd.setText("Add");
		btnAdd.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		btnDelete = new Button(composite, SWT.NONE);
		btnDelete.setText("Delete");
		btnDelete.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
	}

	protected void createIapAcquirerControls(Composite parent, int nColumns) {

		GridLayout layout = new GridLayout(4,true);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		Composite group = new Composite(parent, SWT.NONE);
		group.setFont(parent.getFont());
		group.setLayout(layout);
		group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, nColumns, 1));
		
		Label lbl = new Label(group,SWT.NONE);
		lbl.setText(Messages.NewUseCaseWizard_IapAcquirer);
		lbl.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, false, false, 1, 1));

        cboAcquirer = new AcquirerCombo(group);
        cboAcquirer.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
        cboAcquirer.getCombo().addSelectionListener(new SelectionAdapter() {
        	public void widgetSelected(SelectionEvent e) {
        	}
        });

	}
	protected void createIapIssuerControls(Composite parent, int nColumns) {

		GridLayout layout = new GridLayout(4,true);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		Composite group = new Composite(parent, SWT.NONE);
		group.setFont(parent.getFont());
		group.setLayout(layout);
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, nColumns, 2));
		
		Label lbl = new Label(group,SWT.NONE);
		lbl.setText(Messages.NewUseCaseWizard_IapIssuer);
		lbl.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, false, false, 1, 1));

		cboIssuer = new IssuerCombo(group);
		cboIssuer.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
        cboIssuer.getCombo().addSelectionListener(new SelectionAdapter() {
        	public void widgetSelected(SelectionEvent e) {
        	}
        });

	}
	protected void createMtiControls(Composite parent, int nColumns) {
		GridLayout layout = new GridLayout(4,true);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		Composite group = new Composite(parent, SWT.NONE);
		group.setFont(parent.getFont());
		group.setLayout(layout);
		group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, nColumns, 1));
		
		Label lbl = new Label(group,SWT.NONE);
		lbl.setText(Messages.NewUseCaseWizard_MessageTypeIndicator);
		lbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		txtMti = new VText(group, SWT.BORDER);
		txtMti.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		txtMti.valid();
	}
	private boolean isValidMti() {
		String mti = txtMti.getText();
		StatusInfo status = new StatusInfo();
		boolean valid = true;
		if (mti == null) {
			valid = false;
			status.setError("Message Length Indicator cannot be null");
		} else if (mti.length() != 4) {
			valid = false;
			status.setError("The length of Message Length Indicator must be 4");
		} else if (!mti.matches("[0-9]+")) {
			valid = false;
			status.setError("No numeric character(s) in Message Length Indicator");
		}
		StatusUtil.applyToStatusLine(this, status);
		txtMti.valid(valid);
		validateControls();
		return valid;
	}
	private void hookListeners() {
		btnAdd.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				CopyUseCaseWizardFieldsPage.this.tblFields.add(new FieldsTableViewer.Field("key","value"));
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		btnDelete.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TableItem[] selection = CopyUseCaseWizardFieldsPage.this.tblFields.getTable().getSelection();
				if (selection == null || selection.length == 0) {
					return;
				}
				for (TableItem item : selection) {
					CopyUseCaseWizardFieldsPage.this.tblFields.remove(item.getData());
				}
				if (tblFields.getTable().getItemCount() > 0) {
					tblFields.getTable().select(0);
				}
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		ModifyListener listenerMti = (ModifyEvent e)->{
			if (isValidMti()) {
				validateControls();
			}
		};
		txtMti.addModifyListener(listenerMti);
	}
	private void error(String msg) {
		StatusInfo status = new StatusInfo();
		status.setError(msg);
		setPageComplete(!status.matches(IStatus.ERROR));
		StatusUtil.applyToStatusLine(this, status);
	}
	@Override
	public boolean isPageComplete() {
		return txtMti.isValid();// && txtMsg.isValid();
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
		if (txtMti.isValid()) {
			setPageComplete(true);
			return true;
		}
		return false;
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible) {
			//txtMsg.setFocus();
		}
	}
	public void setMsgTemplate(String template) {
		//txtMsg.setText(template);
	}

	public ISocket getAcquirer() {
		if (cboAcquirer.getStructuredSelection() == null) {
			return null;
		}
		return (ISocket)cboAcquirer.getStructuredSelection().getFirstElement();
	}

	public ISocket getIssuer() {
		if (cboIssuer.getStructuredSelection() == null) {
			return null;
		}
		return (ISocket)cboIssuer.getStructuredSelection().getFirstElement();
	}
	public String getMti() {
		return txtMti.getText();
	}
	public Map<String,String> getFieldsToReplace() {
		if (fieldsToReplace == null) {
			fieldsToReplace = tblFields.getFields();
		}
		return fieldsToReplace;
	}	
}
