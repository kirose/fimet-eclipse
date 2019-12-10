package com.fimet.core.impl.swt.msgiso;

import java.util.List;

import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.fimet.commons.converter.Converter;
import com.fimet.commons.preference.IPreference;
import com.fimet.commons.utils.StringUtils;
import com.fimet.core.IEnviromentManager;
import com.fimet.core.IMessageIsoManager;
import com.fimet.core.Manager;
import com.fimet.core.ISO8583.parser.IParser;
import com.fimet.core.ISO8583.parser.Message;
import com.fimet.core.entity.sqlite.MessageIso;
import com.fimet.core.entity.sqlite.pojo.MessageIsoParameters;
import com.fimet.core.impl.Activator;
import com.fimet.core.impl.preferences.History;
import com.fimet.core.impl.swt.EnviromentTypeCombo;
import com.fimet.core.impl.swt.ParserCombo;
import com.fimet.core.impl.swt.msg.MessageViewer;
import com.fimet.core.net.ISocket;
import com.fimet.parser.util.ParserUtils;
import com.fimet.persistence.sqlite.dao.MessageIsoDAO;

public class MessageIsoViewer extends Composite implements IMessageIsoMonitor {
	private MessageIsoTable table;
	private MessageViewer message;
	
	private Button btnSearch;
	private Button btnClean;
	private MessageIsoTypeCombo cboType;
	private EnviromentTypeCombo cboEnviroment;
	private ParserCombo cboParser;
	private Button btnAsc;
	private Text txtName;
	private Text txtMerchant;
	private Text txtMti;
	private Text txtProcessingCode;
	private History<MessageIso> history;
	private MessageIsoParameters params;
	private IMessageIsoManager messageIsoManager = Manager.get(IMessageIsoManager.class);
	public MessageIsoViewer(MessageIsoParameters inputParams, boolean editable, Composite parent, int style) {
		super(parent, style);
		if (editable) {
			history = new History<MessageIso>();
		}
		params = inputParams;
		setBackground(parent.getBackground());
		createContents(this, editable, inputParams);
	}

	private void createContents(Composite composite, boolean editable, MessageIsoParameters inputParams) {
		
		
        GridData gd;
        GridLayout layout;
        
		layout = new GridLayout(1, true);
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        composite.setLayout(layout);
        composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        
		layout = new GridLayout(6, true);
        layout.marginTop = 0;
        layout.marginBottom = 5;
        layout.marginWidth = 5;
        Group groupSearch = new Group(composite, SWT.NONE);
        //groupSearch.setText("Search");
        groupSearch.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        groupSearch.setLayout(layout);
        groupSearch.setBackground(composite.getBackground());
		Label lbl;
		if (inputParams == null || inputParams.getIdTypeEnviroment() == null) {
			lbl = new Label(groupSearch,SWT.NONE);
			lbl.setText("EnviromentType:");
			lbl.setBackground(composite.getBackground());
			lbl.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, false, false, 1, 1));
			
			cboEnviroment = new EnviromentTypeCombo(groupSearch);
			cboEnviroment.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
			if (Manager.get(IEnviromentManager.class).getActive() != null) {
				cboEnviroment.select(Manager.get(IEnviromentManager.class).getActive().getId());
			}
		}
		if (inputParams == null || inputParams.getIdParser() == null) {
			lbl = new Label(groupSearch,SWT.NONE);
			lbl.setText("Parser:");
			lbl.setBackground(composite.getBackground());
			lbl.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, false, false, 1, 1));
			
			cboParser = new ParserCombo(groupSearch, IParser.ISO8583);
			cboParser.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		}

		lbl = new Label(groupSearch,SWT.NONE);
		lbl.setText("Name:");
		lbl.setBackground(composite.getBackground());
		lbl.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, false, false, 1, 1));

		txtName = new Text(groupSearch, SWT.BORDER);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		lbl = new Label(groupSearch,SWT.NONE);
		lbl.setText("Merchant:");
		lbl.setBackground(composite.getBackground());
		lbl.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, false, false, 1, 1));

		txtMerchant = new Text(groupSearch, SWT.BORDER);
		txtMerchant.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtMerchant.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		lbl = new Label(groupSearch,SWT.NONE);
		lbl.setText("MTI:");
		lbl.setBackground(composite.getBackground());
		lbl.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, false, false, 1, 1));

		txtMti = new Text(groupSearch, SWT.BORDER);
		txtMti.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtMti.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		lbl = new Label(groupSearch,SWT.NONE);
		lbl.setText("Proc Code:");
		lbl.setBackground(composite.getBackground());
		lbl.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, false, false, 1, 1));

		txtProcessingCode = new Text(groupSearch, SWT.BORDER);
		txtProcessingCode.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtProcessingCode.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		if (inputParams == null || inputParams.getType() == null) {
			lbl = new Label(groupSearch,SWT.NONE);
			lbl.setText("Type:");
			lbl.setBackground(composite.getBackground());
			lbl.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, false, false, 1, 1));
			
			cboType = new MessageIsoTypeCombo(groupSearch);
			cboType.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		}
		
		if (inputParams == null || inputParams.getAsc() == null) {
			lbl = new Label(groupSearch,SWT.NONE);
			lbl.setText("ASC:");
			lbl.setBackground(composite.getBackground());
			lbl.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, false, false, 1, 1));
		
			btnAsc = new Button(groupSearch, SWT.CHECK);
			btnAsc.setText("Asc or Desc");
			btnAsc.setBackground(composite.getBackground());
			btnAsc.setSelection(inputParams != null && inputParams.getAsc() != null ? inputParams.getAsc() : true);
			btnAsc.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		}
        
		Composite compositeBtns = new Composite(groupSearch, SWT.NONE);
        compositeBtns.setBackground(composite.getBackground());
        layout = new GridLayout(6,true);
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        compositeBtns.setLayout(layout);
        compositeBtns.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 6, 1));
		
		lbl = new Label(compositeBtns,SWT.NONE);
		lbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		lbl.setBackground(composite.getBackground());
        
		btnClean = new Button(compositeBtns, SWT.NONE);
		btnClean.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnClean.setText("Clean");
		btnClean.setFocus();
		
		btnSearch = new Button(compositeBtns, SWT.NONE);
		btnSearch.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnSearch.setText("Search");
		btnSearch.setFocus();
        
        gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
        layout = new GridLayout(1,true);
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        composite.setLayout(layout);
        composite.setLayoutData(gd);
		
	    gd = new GridData(SWT.FILL, SWT.FILL, true, true,1,1);
        gd.widthHint = 400;
        layout = new GridLayout(2,false);
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        
		if (!editable) {
	    
			SashForm parent = new SashForm(composite, SWT.HORIZONTAL);
	        parent.setLayout(layout);
	        parent.setLayoutData(gd);
	        parent.setFont(parent.getFont());
	        parent.setBackground(composite.getBackground());
			
	        gd = new GridData(SWT.FILL, SWT.FILL, true, true,1,1);
	        layout = new GridLayout(1,false);
	        layout.marginWidth = 0;
	        layout.marginHeight = 0;
	        Composite cmpTable = new Composite(parent, SWT.NONE);
	        cmpTable.setLayout(layout);
	        cmpTable.setLayoutData(gd);
	        cmpTable.setBackground(composite.getBackground());
	
			table = new MessageIsoTable(this, editable, cmpTable, SWT.SINGLE | SWT.H_SCROLL| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);

	        gd = new GridData(SWT.FILL, SWT.FILL, true, true,1,1);
	        layout = new GridLayout(1,false);
	        layout.marginWidth = 0;
	        layout.marginHeight = 0;
	        Composite cmpMessage = new Composite(parent, SWT.NONE);
	        cmpMessage.setLayout(layout);
	        cmpMessage.setLayoutData(gd);
	        cmpMessage.setBackground(composite.getBackground());
			message = new MessageViewer(this, false, cmpMessage, SWT.BORDER);
			parent.setWeights(new int[] {1,1});
		} else {
			table = new MessageIsoTable(this, editable, composite, SWT.SINGLE | SWT.H_SCROLL| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		}
		
		
    	hookListeners(editable);
	}
	private void hookListeners(boolean editable) {
		if (!editable) {
			table.getTable().addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					MessageIsoViewer.this.message.setMessage(parse(table.getSelected()));
				}
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {}
			});
		}
		btnClean.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				doClear();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
    	btnSearch.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				doSearch();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});		
	}
	private Message parse(MessageIso iso) {
		try {
			byte[] isoAndMli = Converter.hexToAscii(iso.getMessage().getBytes());
			return (Message)ParserUtils.parseMessage(isoAndMli, iso.getIdParser());
		} catch (Exception ex) {
			Activator.getInstance().warning("Error parsing message ",ex);
		}
		return null;
	}
	private void doClear() {
		txtName.setText("");
		txtMerchant.setText("");
		txtMti.setText("");
		txtProcessingCode.setText("");
		if (cboEnviroment != null) {
			cboEnviroment.getCombo().deselectAll();
		}
		if (cboParser != null) {
			cboParser.getCombo().deselectAll();
		}
		if (cboType != null) {
			cboType.getCombo().deselectAll();
		}
		if (btnAsc != null) {
			btnAsc.setSelection(true);
		}
	}
	public void doSearch() {
		fillParameters();
		List<MessageIso> messages = MessageIsoDAO.getInstance().findByParameters(params);
		table.setInput(messages);
	}
	private void fillParameters() {
		params.setName(StringUtils.isBlank(txtName.getText()) ? null : txtName.getText());
		params.setMerchant(StringUtils.isBlank(txtMerchant.getText()) ? null : txtMerchant.getText());
		params.setMti(StringUtils.isBlank(txtMti.getText()) ? null : txtMti.getText());
		params.setProcessingCode(StringUtils.isBlank(txtProcessingCode.getText()) ? null : txtProcessingCode.getText());
		if (cboType != null) {
			params.setType(cboType.getSelected() == null ? null : cboType.getSelected().getId());
		}
		if (cboEnviroment != null) {
			params.setIdTypeEnviroment(cboEnviroment.getSelected() == null ? null : cboEnviroment.getSelected().getId());
		}
		if (cboParser != null) {
			params.setIdParser(cboParser.getSelected() == null ? null : cboParser.getSelected().getId());
		}
		if (btnAsc != null) {
			params.setAsc(btnAsc.getSelection());
		}
	}
	@Override
	public void onModifyMessage() {
		
	}

	@Override
	public ISocket getConnection() {
		return null;
	}

	public void addTableDoubleClickListener(IDoubleClickListener listener) {
		table.addDoubleClickListener(listener);
	}

	public MessageIso getSelectedMessage() {
		return table.getSelectedMessage();
	}

	@Override
	public ISelectionProvider getSelectionProvider() {
		return null;
	}

	@Override
	public void onNew() {

	}

	@Override
	public void onEdit() {
		if (table.getSelectedMessage() != null) {
			MessageIsoDialog dialog = new MessageIsoDialog(table.getSelectedMessage(), getShell(), SWT.NONE);
			dialog.open();
			if (dialog.getOutput() != null) {
				history.update(dialog.getOutput());
				table.refresh(dialog.getOutput());
			}
		}
	}

	@Override
	public void onDelete() {
		if (table.getSelectedMessage() != null && askDelete(table.getSelectedMessage())) {
			history.delete(table.getSelectedMessage());
			table.remove(table.getSelectedMessage());
		}
	}
	public void commit() {
		List<MessageIso> deletes = history.getDeletes();
		for (MessageIso e : deletes) {
			messageIsoManager.delete(e);				
		}
		List<MessageIso> updates = history.getUpdates();
		for (MessageIso e : updates) {
			messageIsoManager.update(e);				
		}
		List<MessageIso> inserts = history.getInserts();
		for (MessageIso e : inserts) {
			messageIsoManager.insert(e);				
		}
		history = new History<MessageIso>();
	}
	private boolean askDelete(MessageIso m) {
		//IPreferenceStore store = IDEWorkbenchPlugin.getDefault().getPreferenceStore();
		IPreferenceStore store = Activator.getInstance().getPreferenceStore();
		boolean delete = store.getBoolean(IPreference.DELETE_MESSAGE_ISO);
		if (delete) {
			return true;
		}
		MessageDialogWithToggle dialog = MessageDialogWithToggle.openOkCancelConfirm(
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
			"Delete Message ISO8583",
			"Do you want to delete the message \""+m.getName()+"\"?",
			"Don't ask again.",
			false,
			store,
			IPreference.DELETE_MESSAGE_ISO
		);
		delete = dialog.getReturnCode() == Window.OK;
		if (dialog.getToggleState() && delete) {
			store.setValue(IPreference.DELETE_MESSAGE_ISO, delete);
		}
		return delete;
	}
}
