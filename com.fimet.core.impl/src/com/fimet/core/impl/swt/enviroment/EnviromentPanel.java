package com.fimet.core.impl.swt.enviroment;

import java.util.List;

import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.PlatformUI;

import com.fimet.commons.history.HistoryGroup;
import com.fimet.commons.preference.IPreference;
import com.fimet.commons.utils.ThreadUtils;
import com.fimet.core.IEnviromentManager;
import com.fimet.core.IPreferencesManager;
import com.fimet.core.Manager;
import com.fimet.core.entity.sqlite.Enviroment;
import com.fimet.core.entity.sqlite.EnviromentType;
import com.fimet.core.impl.Activator;
import com.fimet.core.listener.IEnviromentConnected;
import com.fimet.core.listener.IEnviromentConnecting;
import com.fimet.core.listener.IEnviromentDeleted;
import com.fimet.core.listener.IEnviromentDisconnected;
import com.fimet.core.listener.IEnviromentInserted;
import com.fimet.core.listener.IEnviromentUpdated;

public class EnviromentPanel extends Composite implements IEnviromentConnected, IEnviromentConnecting, IEnviromentDisconnected, IEnviromentInserted, IEnviromentUpdated, IEnviromentDeleted {

	public static final String EXTENSION_ID = "com.fimet.view.Enviroment";

	public static int NONE = 0;
	public static int BUTTONS = 1;
	private IEnviromentManager enviromentManager = Manager.get(IEnviromentManager.class);
	private EnviromentTable table;
	private EnviromentButtons buttons;
	private EnviromentType enviromentType;
	boolean autocommit;
	HistoryGroup<Enviroment> history;
	public EnviromentPanel(Composite parent) {
		this(parent, BUTTONS, true);
	}
	public EnviromentPanel(Composite parent, int stylePanel, boolean autocommit) {
		super(parent, SWT.NONE);
		this.autocommit = autocommit;
		if (autocommit) {
			enviromentManager.addListener(ON_INSERT, this);
			enviromentManager.addListener(ON_DELETE, this);
			enviromentManager.addListener(ON_UPDATE, this);
		} else {
			history = new HistoryGroup<Enviroment>();
		}
		enviromentManager.addListener(ON_CONNECTED, this);
		enviromentManager.addListener(ON_CONNECTING, this);
		enviromentManager.addListener(ON_DISCONNECTED, this);
		
		createPartControl(this, stylePanel);
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite composite,int stylePanel) {

		GridLayout layout = new GridLayout((stylePanel & BUTTONS) > 0 ? 2 : 1, false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,1,1));
		
        table = new EnviromentTable(this, composite, SWT.SINGLE | SWT.H_SCROLL| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
        if ((stylePanel & BUTTONS)>0) {
        	buttons = new EnviromentButtons(this, composite, SWT.NONE);
        	buttons.setEnabled(false);
        }

		hookListeners();
	}
	
	private void hookListeners() {
		table.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				onEdit();
			}
		});
        table.getTable().addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Table table = (Table)e.widget;
				onChangeTableSelection(table.getSelection() != null && table.getSelection().length > 0 ? (Enviroment)table.getSelection()[0].getData() : null);
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public boolean setFocus() {
		return super.setFocus();
		//this.table.getControl().setFocus();
		//return true;
	}
	public void onNew() {
		EnviromentDialog dialog = new EnviromentDialog(enviromentType,getShell(),SWT.NONE);
		dialog.open();
		if (dialog.getEnviroment() != null) {
			Enviroment e = dialog.getEnviroment();
			if (autocommit) {
				enviromentManager.insert(e);
			} else {
				history.insert(enviromentType.getId(), e);
				table.add(e);
			}
			if (dialog.getAuthoconnect()) {
				Manager.get(IPreferencesManager.class).save(IPreferencesManager.ENVIROMENT_AUTOSTART,e.getId());
				enviromentManager.start(e);
			}
			onChangeTableSelection(e);
		}
	}

	public void onEdit() {
		if (table.getSelected() != null) {
			Enviroment db = table.getSelected();
			EnviromentDialog dialog = new EnviromentDialog(enviromentType,db, getShell(),SWT.NONE);
			dialog.open();
			if (dialog.getEnviroment() != null) {
				if (autocommit) {
					enviromentManager.update(db);
				} else {
					history.update(enviromentType.getId(), db);
					table.refresh(db);
				}
				if (dialog.getAuthoconnect()) {
					Manager.get(IPreferencesManager.class).save(IPreferencesManager.ENVIROMENT_AUTOSTART,db.getId());
					enviromentManager.start(db);
				}
				onChangeTableSelection(db);
			}
		}
	}

	public void onDelete() {
		TableItem[] selection = table.getTable().getSelection();
		if (selection != null && selection.length > 0) {
			Enviroment e = (Enviroment)selection[0].getData();
			if (askDeleteEnviroment(e)) {
				if (autocommit) {
					enviromentManager.delete(e);
				} else {
					history.delete(enviromentType.getId(), e);
					table.remove(e);
				}
			}
		}
	}

	public void onConnect() {
		TableItem[] selection = table.getTable().getSelection();
		if (selection != null && selection.length > 0) {
			Enviroment db = (Enviroment)selection[0].getData();
			if (!db.isConnected()) {
				if (buttons != null) {
					buttons.setConnectEnabled(false);
					buttons.setDisconnectEnabled(true);
				}
				enviromentManager.start(db);
			}
		}
	}

	public void onDiscconect() {
		TableItem[] selection = table.getTable().getSelection();
		if (selection != null && selection.length > 0) {
			Enviroment e = (Enviroment)selection[0].getData();
			if (e.isConnected()) {
				if (buttons != null) {
					buttons.setConnectEnabled(true);
					buttons.setDisconnectEnabled(false);
				}
				enviromentManager.stop(e);
			}
		}
	}
	private boolean askDeleteEnviroment(Enviroment node) {
		//IPreferenceStore store = IDEWorkbenchPlugin.getDefault().getPreferenceStore();
		IPreferenceStore store = Activator.getInstance().getPreferenceStore();
		boolean delete = store.getBoolean(IPreference.DELETE_ENVIROMENT);
		if (delete) {
			return true;
		}
		MessageDialogWithToggle dialog = MessageDialogWithToggle.openOkCancelConfirm(
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
			"Delete Enviroment",
			"Do you want to delete the enviroment \""+node.getName()+"\"?",
			"Don't ask again.",
			false,
			store,
			IPreference.DELETE_ENVIROMENT
		);
		delete = dialog.getReturnCode() == Window.OK;
		if (dialog.getToggleState() && delete) {
			store.setValue(IPreference.DELETE_ENVIROMENT, delete);
		}
		return delete;
	}
	public void onChangeTableSelection(Enviroment e) {
		if (buttons != null) {
			if (e != null) {
				buttons.setEditEnabled(true);
				buttons.setDeleteEnabled(true);
				if (e.isConnected()) {
					buttons.setDisconnectEnabled(true);
					buttons.setConnectEnabled(false);
				} else {
					buttons.setDisconnectEnabled(false);
					buttons.setConnectEnabled(true);
				}
			} else {
				buttons.setEnabled(false);
			}
		}
	}
	public EnviromentType getEnviromentType() {
		return enviromentType;
	}
	public void setEnviromentType(EnviromentType enviromentType) {
		this.enviromentType = enviromentType;
	}
	public void removeListeners() {
		enviromentManager.removeListener(ON_CONNECTED, this);
		enviromentManager.removeListener(ON_CONNECTING, this);
		enviromentManager.removeListener(ON_DISCONNECTED, this);
		if (autocommit) {
			enviromentManager.removeListener(ON_INSERT, this);
			enviromentManager.removeListener(ON_DELETE, this);
			enviromentManager.removeListener(ON_UPDATE, this);
		}
	}
	@Override
	public void dispose() {
		removeListeners();
		super.dispose();
	}
	public Enviroment getSelected() {
		return table.getSelected();
	}

	@Override
	public void onEnviromentDeleted(Enviroment e) {
		ThreadUtils.runOnMainThread(()->{table.remove(e);});
	}

	@Override
	public void onEnviromentUpdated(Enviroment e) {
		ThreadUtils.runOnMainThread(()->{table.refresh(e);});
	}

	@Override
	public void onEnviromentInserted(Enviroment e) {
		ThreadUtils.runOnMainThread(()->{table.insert(e,-1);});		
	}

	@Override
	public void onEnviromentDisconnected(Enviroment e) {
		ThreadUtils.runOnMainThread(()->{table.refresh(e);});		
	}

	@Override
	public void onEnviromentConnecting(Enviroment e) {
		ThreadUtils.runOnMainThread(()->{table.refresh(e);});
	}

	@Override
	public void onEnviromentConnected(Enviroment e) {
		ThreadUtils.runOnMainThread(()->{table.refresh(e);});
	}
	public void setInput(List<Enviroment> enviroments) {
		table.setInput(enviroments);
	}
	public void setInput(EnviromentType type, List<Enviroment> enviroments) {
		enviromentType = type;
		if (history != null && enviroments != null && type != null && !history.isEmpty(type.getId())) {
			List<Enviroment> inserts = history.getInserts();
			List<Enviroment> updates = history.getUpdates();
			List<Enviroment> deletes = history.getDeletes();
			enviroments.removeAll(deletes);
			enviroments.removeAll(updates);
			enviroments.addAll(updates);
			enviroments.addAll(inserts);
		}
		table.setInput(enviroments);
	}
	public void add(Enviroment sm) {
		table.add(sm);
	}
	public void refresh(Enviroment sm) {
		table.refresh(sm);
	}
	public void remove(Enviroment sm) {
		table.remove(sm);
	}
	public void commit() {
		if (!autocommit) {
			List<Enviroment> deletes = history.getDeletes();
			for (Enviroment e : deletes) {
				enviromentManager.delete(e);				
			}
			List<Enviroment> updates = history.getUpdates();
			for (Enviroment e : updates) {
				enviromentManager.update(e);				
			}
			List<Enviroment> inserts = history.getInserts();
			for (Enviroment e : inserts) {
				enviromentManager.insert(e);				
			}
		}		
	}
	public void select(int i) {
		table.getTable().setSelection(0);
		onChangeTableSelection((Enviroment)table.getTable().getItem(0).getData());		
	}
}
