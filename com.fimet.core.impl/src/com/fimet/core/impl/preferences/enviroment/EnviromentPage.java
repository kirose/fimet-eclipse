package com.fimet.core.impl.preferences.enviroment;


import java.util.List;

import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;

import com.fimet.commons.Color;
import com.fimet.commons.history.HistoryGroup;
import com.fimet.commons.preference.IPreference;
import com.fimet.core.IEnviromentManager;
import com.fimet.core.Manager;
import com.fimet.core.entity.sqlite.EnviromentType;
import com.fimet.core.impl.Activator;
import com.fimet.core.impl.swt.enviroment.EnviromentPanel;

public class EnviromentPage extends PreferencePage implements IWorkbenchPreferencePage {
	public static final String ID = "com.fimet.preferences.EnviromentPage";
	private TypeTable tableType;
	private EnviromentPanel panelEnviroment;
	private IEnviromentManager enviromentManager = Manager.get(IEnviromentManager.class);
	HistoryGroup<EnviromentType> historyTypes;
	Button btnApply;
    public EnviromentPage() {
        noDefaultAndApplyButton();
        historyTypes = new HistoryGroup<EnviromentType>();
    }
    @Override
    protected final Control createContents(Composite container) {
        GridData gd;
        GridLayout layout;
        gd = new GridData(SWT.FILL, SWT.FILL, true, true);
        layout = new GridLayout(1,false);
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        container.setLayout(layout);
        container.setLayoutData(gd);
		
	    gd = new GridData(SWT.FILL, SWT.FILL, true, true);
        //gd.heightHint = 200;
        gd.widthHint = 600;
        layout = new GridLayout(2,false);
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        
        SashForm parent = new SashForm(container, SWT.HORIZONTAL);
        parent.setLayout(layout);
        parent.setLayoutData(gd);
        parent.setFont(parent.getFont());
        parent.setBackground(Color.WHITE);
		
        gd = new GridData(SWT.FILL, SWT.FILL, true, true,1,1);
        layout = new GridLayout(1,false);
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        Composite cmpType = new Composite(parent, SWT.NONE);
        cmpType.setLayout(layout);
        cmpType.setLayoutData(gd);

        gd = new GridData(SWT.FILL, SWT.FILL, true, true,1,1);
        layout = new GridLayout(1,false);
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        Composite cmpMessage = new Composite(parent, SWT.NONE);
        cmpMessage.setLayout(layout);
        cmpMessage.setLayoutData(gd);
		
		tableType = new TypeTable(this, cmpType);
		panelEnviroment = new EnviromentPanel(cmpMessage, NONE, false);

		parent.setWeights(new int[] {2,6});
		

    	Composite cmpButtons = new Composite(container, SWT.NONE);
    	layout = new GridLayout(2,false);
    	layout.marginHeight = 0;
    	layout.marginWidth = 0;
    	cmpButtons.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,1,1));
    	cmpButtons.setLayout(layout);
    	new Label(cmpButtons, SWT.NONE).setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnApply = new Button(cmpButtons, SWT.NONE);
		btnApply.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnApply.setText("    Apply    ");
		btnApply.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				commit();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		
		tableType.setInput(enviromentManager.getEnviromentsTypes());
		tableType.addTableSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				loadEnviromentsIntoTable(tableType.getSelected());
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});

        return parent;
    }
    private void loadEnviromentsIntoTable(EnviromentType type) {
		panelEnviroment.setInput(type, type != null ? enviromentManager.getEnviromentsByIdType(type.getId()) : null);
	}    	
	@Override
    public void init(IWorkbench workbench) {
    }
    @Override
    public boolean performOk() {
    	commit();
    	return super.performOk();
    }

	public void onNewType() {
		TypeDialog dialog = new TypeDialog(getShell(), SWT.NONE);
		dialog.open();
		EnviromentType sm = dialog.getEnviromentType();
		if (sm != null) {
			tableType.add(sm);
			historyTypes.insert(sm.getId(), sm);
			loadEnviromentsIntoTable(tableType.getSelected());
		}
	}
	public void onEditType() {
		if (tableType.getSelected() != null) {
			TypeDialog dialog = new TypeDialog(tableType.getSelected(), getShell(), SWT.NONE);
			dialog.open();
			EnviromentType sm = dialog.getEnviromentType();
			if (sm != null) {
				tableType.update(sm);
				historyTypes.update(sm.getId(), sm);
			}
		}
	}
	public void onDeleteType() {
		if (tableType.getSelected() != null) {
			EnviromentType sm = tableType.getSelected();
			if (askDeleteEnviromentType(sm)) {
				tableType.delete(sm);
				historyTypes.delete(sm.getId(), sm);
			}
		}
	}
	public void commit() {
		List<EnviromentType> deletesSimulators = historyTypes.getDeletes();
		for (EnviromentType s : deletesSimulators) {
			enviromentManager.deleteEnviromentType(s);
		}
		List<EnviromentType> updatesTypes = historyTypes.getUpdates();
		for (EnviromentType s : updatesTypes) {
			enviromentManager.updateEnviromentType(s);
		}
		List<EnviromentType> insertTypes = historyTypes.getInserts();
		for (EnviromentType s : insertTypes) {
			enviromentManager.insertEnviromentType(s);
		}
		panelEnviroment.commit();
        historyTypes = new HistoryGroup<EnviromentType>();
	}
	public EnviromentType getSelected() {
		return tableType.getSelected();
	}
	private boolean askDeleteEnviromentType(EnviromentType node) {
		//IPreferenceStore store = IDEWorkbenchPlugin.getDefault().getPreferenceStore();
		IPreferenceStore store = Activator.getInstance().getPreferenceStore();
		boolean delete = store.getBoolean(IPreference.DELETE_ENVIROMENT_TYPE);
		if (delete) {
			return true;
		}
		MessageDialogWithToggle dialog = MessageDialogWithToggle.openOkCancelConfirm(
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
			"Delete Enviroment Type",
			"Do you want to delete the enviroment type \""+node.getName()+"\"?\nWARNING:This action will delete: Enviroments, Rules and Sockets associated.",
			"Don't ask again.",
			false,
			store,
			IPreference.DELETE_ENVIROMENT_TYPE
		);
		delete = dialog.getReturnCode() == Window.OK;
		if (dialog.getToggleState() && delete) {
			store.setValue(IPreference.DELETE_ENVIROMENT_TYPE, delete);
		}
		return delete;
	}
	@Override
	public void dispose() {
		panelEnviroment.dispose();
	}
}
