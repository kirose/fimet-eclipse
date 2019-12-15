package com.fimet.core.impl.swt.ftp;

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

import com.fimet.commons.history.History;
import com.fimet.commons.utils.ThreadUtils;
import com.fimet.core.Manager;
import com.fimet.core.entity.sqlite.Ftp;
import com.fimet.core.listener.IFtpConnected;
import com.fimet.core.listener.IFtpConnecting;
import com.fimet.core.listener.IFtpDeleted;
import com.fimet.core.listener.IFtpDisconnected;
import com.fimet.core.listener.IFtpInserted;
import com.fimet.core.listener.IFtpUpdated;
import com.fimet.core.net.IFtpManager;

public class FtpPanel extends Composite implements IFtpConnected, IFtpConnecting, IFtpDisconnected, IFtpInserted, IFtpUpdated, IFtpDeleted {

	private FtpTable table;
	private FtpButtons buttons;
	private History<Ftp> history;
	private static IFtpManager ftpManager = Manager.get(IFtpManager.class);
	private boolean autocommit;

	public FtpPanel(Composite parent, int style, boolean autocommit) {
		super(parent, style);
		this.autocommit = autocommit;
		if (!autocommit) {
			history = new History<Ftp>();
			ftpManager.addListener(ON_INSERT, this);
			ftpManager.addListener(ON_UPDATE, this);
			ftpManager.addListener(ON_DELETE, this);
		}
		ftpManager.addListener(ON_CONNECTED, this);
		ftpManager.addListener(ON_CONNECTING, this);
		ftpManager.addListener(ON_DISCONNECTED, this);
		
		GridLayout layout = new GridLayout(2, false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		setLayout(layout);
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		setFont(parent.getFont());
		
		createPartControl(this);
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite composite) {

        table = new FtpTable(this, composite, SWT.SINGLE | SWT.H_SCROLL| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		buttons = new FtpButtons(this, composite, SWT.NONE);
		buttons.setEnabled(false);

		List<Ftp> ftps = ftpManager.getFtps();
		table.setInput(ftps);
		if (ftps != null && !ftps.isEmpty()) {
			table.getTable().setSelection(0);
			onChangeTableSelection(ftps.get(0));
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
				onChangeTableSelection(table.getSelection() != null && table.getSelection().length > 0 ? (Ftp)table.getSelection()[0].getData() : null);
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
		FtpDialog dialog = new FtpDialog(getShell(),SWT.NONE);
		dialog.open();
		if (dialog.getConnection() != null) {
			Ftp ftp = dialog.getConnection(); 
			if (autocommit) {
				ftpManager.insert(ftp);
			} else {
				history.insert(ftp);
				table.add(ftp);
			}
		}
	}

	public void onEdit() {
		if (table.getConnectionSelected() != null) {
			Ftp ftp = table.getConnectionSelected();
			FtpDialog dialog = new FtpDialog(getShell(),SWT.NONE, ftp);
			dialog.open();
			if (dialog.getConnection() != null) {
				if (autocommit) {
					ftpManager.update(ftp);
				} else {
					history.update(ftp);
					table.refresh(ftp);
				}
			}
		}
	}

	public void onDelete() {
		TableItem[] selection = table.getTable().getSelection();
		if (selection != null && selection.length > 0) {
			Ftp ftp = (Ftp)selection[0].getData();
			boolean ok = MessageDialog.openConfirm(
				getShell(), 
				"Delete",
				"Delete Ftp Connection "+ftp.getName()+"?"
			);
			if (ok) {
				if (autocommit) {
					ftpManager.delete(ftp);
				} else {
					history.delete(ftp);
					table.remove(ftp);
				}
			}
		}
	}

	public void onConnect() {
		TableItem[] selection = table.getTable().getSelection();
		if (selection != null && selection.length > 0) {
			Ftp db = (Ftp)selection[0].getData();
			if (!db.isConnected() && db.getIsValid()) {
				buttons.setConnectEnabled(false);
				buttons.setDisconnectEnabled(true);
				ftpManager.connect(db);
			}
		}
	}

	public void onDiscconect() {
		TableItem[] selection = table.getTable().getSelection();
		if (selection != null && selection.length > 0) {
			Ftp db = (Ftp)selection[0].getData();
			if (db.isConnected()) {
				buttons.setConnectEnabled(true);
				buttons.setDisconnectEnabled(false);
				ftpManager.disconnect(db);
			}
		}
	}
	public void onChangeTableSelection(Ftp db) {
		if (db != null) {
			buttons.setEditEnabled(true);
			buttons.setDeleteEnabled(true);
			if (db.isConnected()) {
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
		ftpManager.removeListener(ON_CONNECTED, this);
		ftpManager.removeListener(ON_CONNECTING, this);
		ftpManager.removeListener(ON_DISCONNECTED, this);
		if (!autocommit) {
			ftpManager.removeListener(ON_INSERT, this);
			ftpManager.removeListener(ON_UPDATE, this);
			ftpManager.removeListener(ON_DELETE, this);
		}
	}
	@Override
	public void dispose() {
		removeListeners();
		super.dispose();
	}
	public void commit() {
		if (!autocommit) {
			List<Ftp> deletesMessages = history.getDeletes();
			for (Ftp m: deletesMessages) {
				ftpManager.delete(m);
			}
			List<Ftp> updates = history.getUpdates();
			for (Ftp m : updates) {
				ftpManager.update(m);
			}
			List<Ftp> inserts = history.getInserts();
			for (Ftp m : inserts) {
				ftpManager.insert(m);
			}
			history = new History<Ftp>();
		}
	}
	@Override
	public void onFtpDeleted(Ftp ftp) {
		ThreadUtils.runOnMainThread(()->{table.remove(ftp);});
	}

	@Override
	public void onFtpUpdated(Ftp ftp) {
		ThreadUtils.runOnMainThread(()->{table.refresh(ftp);});
	}

	@Override
	public void onFtpInserted(Ftp ftp) {
		ThreadUtils.runOnMainThread(()->{table.add(ftp);});
	}

	@Override
	public void onFtpDisconnected(Ftp ftp) {
		ThreadUtils.runOnMainThread(()->{table.refresh(ftp);});
	}

	@Override
	public void onFtpConnecting(Ftp ftp) {
		ThreadUtils.runOnMainThread(()->{table.refresh(ftp);});		
	}

	@Override
	public void onFtpConnected(Ftp ftp) {
		ThreadUtils.runOnMainThread(()->{table.refresh(ftp);});
	}
}
