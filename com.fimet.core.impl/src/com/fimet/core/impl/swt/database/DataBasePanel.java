package com.fimet.core.impl.swt.database;

import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.fimet.commons.utils.ThreadUtils;
import com.fimet.core.IDataBaseManager;
import com.fimet.core.Manager;
import com.fimet.core.entity.sqlite.DataBase;
import com.fimet.core.impl.preferences.History;
import com.fimet.core.listener.IDataBaseConnected;
import com.fimet.core.listener.IDataBaseConnecting;
import com.fimet.core.listener.IDataBaseDeleted;
import com.fimet.core.listener.IDataBaseDisconnected;
import com.fimet.core.listener.IDataBaseInserted;
import com.fimet.core.listener.IDataBaseUpdated;

public class DataBasePanel extends Composite implements IDataBaseConnected, IDataBaseDisconnected, IDataBaseConnecting, IDataBaseInserted, IDataBaseUpdated, IDataBaseDeleted {
	private DataBaseTable table;
	private DataBaseButtons buttons;
	private History<DataBase> history;
	private IDataBaseManager dataBaseManager = Manager.get(IDataBaseManager.class);
	private boolean autocommit;
	public DataBasePanel(Composite parent, int style, boolean autocommit) {
		super(parent, style);
		this.autocommit = autocommit;
		if (!autocommit) {
			history = new History<DataBase>();
			dataBaseManager.addListener(ON_INSERT, this);
			dataBaseManager.addListener(ON_UPDATE, this);
			dataBaseManager.addListener(ON_DELETE, this);
		}
		dataBaseManager.addListener(ON_CONNECTED, this);
		dataBaseManager.addListener(ON_CONNECTING, this);
		dataBaseManager.addListener(ON_DISCONNECTED, this);

		GridLayout layout = new GridLayout(2, false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		setLayout(layout);
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,1,1));
		setFont(parent.getFont());
		
		createPartControl(this);
	}
	

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite composite) {

        table = new DataBaseTable(this, composite, SWT.SINGLE | SWT.H_SCROLL| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
        buttons = new DataBaseButtons(this, composite, SWT.NONE);
		buttons.setEnabled(false);

		List<DataBase> databases = dataBaseManager.getDataBases();
		table.setInput(databases);
		if (databases != null && !databases.isEmpty()) {
			table.getTable().setSelection(0);
			onChangeTableSelection(databases.get(0));
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
				onChangeTableSelection(table.getSelection() != null && table.getSelection().length > 0 ? (DataBase)table.getSelection()[0].getData() : null);
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public boolean setFocus() {
		this.table.getControl().setFocus();
		return true;
	}
	public void onNew() {
		DataBaseDialog dialog = new DataBaseDialog(getShell(),SWT.NONE);
		dialog.open();
		if (dialog.getConnection() != null) {
			DataBase db = dialog.getConnection(); 
			if (autocommit) {
				dataBaseManager.insert(db);
			} else {
				history.insert(db);
				table.add(db);
			}
		}
	}
	public void onEdit() {
		if (table.getSelected() != null) {
			DataBase db = table.getSelected();
			DataBaseDialog dialog = new DataBaseDialog(getShell(),SWT.NONE, db);
			dialog.open();
			if (dialog.getConnection() != null) {
				if (autocommit) {
					dataBaseManager.update(db);
				} else {
					history.update(db);
					table.refresh(db);
				}
				onChangeTableSelection(db);
			}
		}
	}
	public void onDelete() {
		TableItem[] selection = table.getTable().getSelection();
		if (selection != null && selection.length > 0) {
			DataBase db = (DataBase)selection[0].getData();
			boolean ok = MessageDialog.openConfirm(
				getShell(), 
				"Delete",
				"Delete Data Base Connection "+db.getName()+"?"
			);
			if (ok) {
				if (autocommit) {
					dataBaseManager.delete(db);
				} else {
					history.delete(db);
					table.remove(db);
				}
			}
		}
	}
	public void onConnect() {
		TableItem[] selection = table.getTable().getSelection();
		if (selection != null && selection.length > 0) {
			DataBase db = (DataBase)selection[0].getData();
			if (!dataBaseManager.isConnected(db) && db.getIsValid()) {
				buttons.setConnectEnabled(false);
				buttons.setDisconnectEnabled(true);
				dataBaseManager.connect(db);
			}
		}
	}
	public void onDiscconect() {
		TableItem[] selection = table.getTable().getSelection();
		if (selection != null && selection.length > 0) {
			DataBase db = (DataBase)selection[0].getData();
			if (dataBaseManager.isConnected(db)) {
				buttons.setConnectEnabled(true);
				buttons.setDisconnectEnabled(false);
				dataBaseManager.disconnect();
			}
		}
	}
	public void onChangeTableSelection(DataBase db) {
		if (db != null) {
			buttons.setEditEnabled(true);
			buttons.setDeleteEnabled(true);
			if (dataBaseManager.isConnected(db)) {
				buttons.setConnectEnabled(false);
				buttons.setDisconnectEnabled(true);
			} else {
				buttons.setConnectEnabled(db.getIsValid());
				buttons.setDisconnectEnabled(false);
			}
		} else {
			buttons.setEnabled(false);
		}		
	}
	public void removeListeners() {
		dataBaseManager.removeListener(ON_CONNECTED, this);
		dataBaseManager.removeListener(ON_CONNECTING, this);
		dataBaseManager.removeListener(ON_DISCONNECTED, this);
		if (!autocommit) {
			dataBaseManager.removeListener(ON_INSERT, this);
			dataBaseManager.removeListener(ON_UPDATE, this);
			dataBaseManager.removeListener(ON_DELETE, this);
		}
	}
	@Override
	public void dispose() {
		removeListeners();
		super.dispose();
	}

	public void onImport() {
		DataBaseImportDialog dialog = new DataBaseImportDialog(getShell(), SWT.NONE);
		dialog.open();
		if (dialog.getDataBases() != null && !dialog.getDataBases().isEmpty()) {
			List<DataBase> dataBases = dialog.getDataBases();
			for (DataBase dataBase : dataBases) {
				DataBase old = dataBaseManager.getConnection(dataBase.getName());
				if (old != null) {
					dataBase.setId(old.getId());
					old.setAddress(dataBase.getAddress());
					old.setSid(dataBase.getSid());
					old.setPassword(dataBase.getPassword());
					old.setUser(dataBase.getUser());
					old.setPort(dataBase.getPort());
					dataBaseManager.update(old);
				} else {
					dataBaseManager.insert(dataBase);
				}
			}
			table.setInput(dataBaseManager.getDataBases());
		}
	}
	public void commit() {
		if (!autocommit) {
			List<DataBase> deletes = history.getDeletes();
			for (DataBase m: deletes) {
				dataBaseManager.delete(m);
			}
			List<DataBase> updates = history.getUpdates();
			for (DataBase m : updates) {
				dataBaseManager.update(m);
			}
			List<DataBase> inserts = history.getInserts();
			for (DataBase m : inserts) {
				dataBaseManager.insert(m);
			}
			history = new History<DataBase>();
		}
	}
	@Override
	public void onDataBaseConnecting(DataBase e) {
		ThreadUtils.runOnMainThread(()->{table.refresh(e);});
	}
	@Override
	public void onDataBaseDisconnected(DataBase e) {
		ThreadUtils.runOnMainThread(()->{table.refresh(e);});
	}
	@Override
	public void onDataBaseConnected(DataBase e) {
		ThreadUtils.runOnMainThread(()->{table.refresh(e);});		
	}
	@Override
	public void onDataBaseDeleted(DataBase e) {
		ThreadUtils.runOnMainThread(()->{table.remove(e);});
	}
	@Override
	public void onDataBaseUpdated(DataBase e) {
		ThreadUtils.runOnMainThread(()->{table.refresh(e);});
	}
	@Override
	public void onDataBaseInserted(DataBase e) {
		ThreadUtils.runOnMainThread(()->{table.add(e);});
	}
}
