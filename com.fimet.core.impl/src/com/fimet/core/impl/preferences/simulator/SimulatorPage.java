package com.fimet.core.impl.preferences.simulator;


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

import com.fimet.commons.Color;
import com.fimet.commons.history.HistoryGroup;
import com.fimet.commons.preference.IPreference;
import com.fimet.core.ISimulatorManager;
import com.fimet.core.Manager;
import com.fimet.core.entity.sqlite.Simulator;
import com.fimet.core.entity.sqlite.SimulatorMessage;
import com.fimet.core.impl.Activator;
import com.fimet.persistence.sqlite.dao.SimulatorMessageDAO;

public class SimulatorPage extends PreferencePage implements IWorkbenchPreferencePage {
	public static final String ID = "com.fimet.preferences.SimulatorsPage";
	private SimulatorTable tableSimulator;
	private MessageTable tableMessage;
	private ISimulatorManager simulatorManager = Manager.get(ISimulatorManager.class);
	HistoryGroup<Simulator> historySimulators;
	HistoryGroup<SimulatorMessage> historyMessages;
	Button btnApply;
    public SimulatorPage() {
        noDefaultAndApplyButton();
        historySimulators = new HistoryGroup<Simulator>();
    	historyMessages = new HistoryGroup<SimulatorMessage>();
    }
    @Override
    protected final Control createContents(Composite container) {
        GridData gd;
        GridLayout layout;
        gd = new GridData(SWT.FILL, SWT.FILL, true, true);
        //gd.heightHint = 400;
        //gd.widthHint = 600;
        layout = new GridLayout(1,false);
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        container.setLayout(layout);
        container.setLayoutData(gd);
		
	    gd = new GridData(SWT.FILL, SWT.FILL, true, true);
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
        Composite cmpSimulator = new Composite(parent, SWT.NONE);
        cmpSimulator.setLayout(layout);
        cmpSimulator.setLayoutData(gd);

        gd = new GridData(SWT.FILL, SWT.FILL, true, true,1,1);
        layout = new GridLayout(1,false);
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        Composite cmpMessage = new Composite(parent, SWT.NONE);
        cmpMessage.setLayout(layout);
        cmpMessage.setLayoutData(gd);
		
        gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
        gd.heightHint = 200;
		tableSimulator = new SimulatorTable(this, cmpSimulator);
		tableSimulator.getTable().getTable().setLayoutData(gd);
		tableMessage = new MessageTable(this, cmpMessage);
		tableMessage.getTable().getTable().setLayoutData(gd);
		
		parent.setWeights(new int[] {1,2});
		

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
		
		tableSimulator.setInput(simulatorManager.getEntities());
		tableSimulator.addTableSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				loadMessagesIntoTable(tableSimulator.getSelected());
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});

        return parent;
    }
    private void loadMessagesIntoTable(Simulator simulator) {
		if (simulator != null) {
			List<SimulatorMessage> msgs = SimulatorMessageDAO.getInstance().findByIdSmulator(simulator.getId());
			if (!historyMessages.isEmpty(simulator.getId())) {
				List<SimulatorMessage> inserts = historyMessages.getInserts();
				List<SimulatorMessage> updates = historyMessages.getUpdates();
				List<SimulatorMessage> deletes = historyMessages.getDeletes();
				msgs.removeAll(deletes);
				msgs.removeAll(updates);
				msgs.addAll(updates);
				msgs.addAll(inserts);
			}
			tableMessage.setInput(msgs);
		}    	
    }
	@Override
    public void init(IWorkbench workbench) {
    }
    @Override
    public boolean performOk() {
    	commit();
    	return super.performOk();
    }

	public void onNewSimulator() {
		SimulatorDialog dialog = new SimulatorDialog(getShell(), SWT.NONE);
		dialog.open();
		Simulator sm = dialog.getSimulator();
		if (sm != null) {
			tableSimulator.add(sm);
			historySimulators.insert(sm.getId(), sm);
			loadMessagesIntoTable(tableSimulator.getSelected());
		}
	}
	public void onEditSimulator() {
		if (tableSimulator.getSelected() != null) {
			SimulatorDialog dialog = new SimulatorDialog(tableSimulator.getSelected(), getShell(), SWT.NONE);
			dialog.open();
			Simulator sm = dialog.getSimulator();
			if (sm != null) {
				tableSimulator.update(sm);
				historySimulators.update(sm.getId(), sm);
			}
		}
	}
	public void onDeleteSimulator() {
		if (tableSimulator.getSelected() != null) {
			Simulator sm = tableSimulator.getSelected();
			if (askDeleteSimulator(sm)) {
				tableSimulator.delete(sm);
				historySimulators.delete(sm.getId(), sm);
			}
		}
	}
	public void onNewMessage() {
		if (getSelectedSimulator() != null) {
			MessageDialog dialog = new MessageDialog(tableSimulator.getSelected(), getShell(), SWT.NONE);
			dialog.open();
			SimulatorMessage sm = dialog.getSimulatorMessage();
			if (sm != null) {
				tableMessage.add(sm);
				historyMessages.insert(getSelectedSimulator().getId(), sm);
			}
		}
	}
	public void onEditMessage() {
		if (tableMessage.getSelected() != null) {
			MessageDialog dialog = new MessageDialog(tableSimulator.getSelected(), tableMessage.getSelected(), getShell(), SWT.NONE);
			dialog.open();
			SimulatorMessage sm = dialog.getSimulatorMessage();
			if (sm != null) {
				tableMessage.update(sm);
				if (sm.getId() == null) {
					historyMessages.insert(getSelectedSimulator().getId(), sm);
				} else {
					historyMessages.update(getSelectedSimulator().getId(), sm);
				}
			}
		}
	}
	public void onDeleteMessage() {
		if (tableMessage.getSelected() != null) {
			SimulatorMessage sm = tableMessage.getSelected();
			if (askDeleteMessage(sm)) {
				tableMessage.delete(sm);
				historyMessages.delete(getSelectedSimulator().getId(), sm);
			}
		}
	}
	public void commit() {
		List<SimulatorMessage> deletesMessages = historyMessages.getDeletes();
		for (SimulatorMessage m : deletesMessages) {
			simulatorManager.deleteSimulatorMessage(m);
		}
		List<Simulator> deletesSimulators = historySimulators.getDeletes();
		for (Simulator s : deletesSimulators) {
			simulatorManager.deleteSimulator(s);
		}
		List<Simulator> savesSimulators = historySimulators.getSaves();
		for (Simulator s : savesSimulators) {
			simulatorManager.saveSimulator(s);
		}
		List<SimulatorMessage> savesMessages = historyMessages.getSaves();
		for (SimulatorMessage m : savesMessages) {
			simulatorManager.saveSimulatorMessage(m);
		}
		List<Integer> ids = historySimulators.getIds();
		ids.addAll(historyMessages.getIds());
		simulatorManager.freeSimulators(ids);
		
        historySimulators = new HistoryGroup<Simulator>();
    	historyMessages = new HistoryGroup<SimulatorMessage>();
	}
	public Simulator getSelectedSimulator() {
		return tableSimulator.getSelected();
	}
	private boolean askDeleteMessage(SimulatorMessage node) {
		//IPreferenceStore store = IDEWorkbenchPlugin.getDefault().getPreferenceStore();
		IPreferenceStore store = Activator.getInstance().getPreferenceStore();
		boolean delete = store.getBoolean(IPreference.DELETE_SIMULATOR_MESSAGE);
		if (delete) {
			return true;
		}
		MessageDialogWithToggle dialog = MessageDialogWithToggle.openOkCancelConfirm(
			getShell(), 
			"Delete simulator Message",
			"Do you want to delete the message simulator \""+node.getMti()+"\"?",
			"Don't ask again.",
			false,
			store,
			IPreference.DELETE_SIMULATOR_MESSAGE
		);
		delete = dialog.getReturnCode() == Window.OK;
		if (dialog.getToggleState() && delete) {
			store.setValue(IPreference.DELETE_SIMULATOR_MESSAGE, delete);
		}
		return delete;
	}
	private boolean askDeleteSimulator(Simulator node) {
		//IPreferenceStore store = IDEWorkbenchPlugin.getDefault().getPreferenceStore();
		IPreferenceStore store = Activator.getInstance().getPreferenceStore();
		boolean delete = store.getBoolean(IPreference.DELETE_SIMULATOR_AND_MESSAGES);
		if (delete) {
			return true;
		}
		MessageDialogWithToggle dialog = MessageDialogWithToggle.openOkCancelConfirm(
			getShell(), 
			"Delete simulator Message",
			"Do you want to delete the simulator \""+node.getName()+"\" and messages?",
			"Don't ask again.",
			false,
			store,
			IPreference.DELETE_SIMULATOR_AND_MESSAGES
		);
		delete = dialog.getReturnCode() == Window.OK;
		if (dialog.getToggleState() && delete) {
			store.setValue(IPreference.DELETE_SIMULATOR_AND_MESSAGES, delete);
		}
		return delete;
	}
}
