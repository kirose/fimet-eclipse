package com.fimet.core.impl.swt.enviroment;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class EnviromentButtons extends Composite {
	private EnviromentPanel viewer;
	private Button btnNew;
	private Button btnEdit;
	private Button btnDelete;
	private Button btnConnect;
	private Button btnDisconnect;
	public EnviromentButtons(EnviromentPanel viewer, Composite parent, int style) {
		super(parent, style);
		this.viewer = viewer;
		//setBackground(parent.getBackground());
		setLayout(new GridLayout(1,true));
		setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, true,1,1));
		createContents();
	}
	public void createContents() {
		Composite parent = this;
		btnNew = new Button(parent, SWT.NONE);
		btnNew.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnNew.setText("New");
		
		btnDelete = new Button(parent, SWT.NONE);
		btnDelete.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnDelete.setText("Delete");

		btnEdit = new Button(parent, SWT.NONE);
		btnEdit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnEdit.setText("Edit");

		btnConnect = new Button(parent, SWT.NONE);
		btnConnect.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnConnect.setText("Connect");
		
		btnDisconnect = new Button(parent, SWT.NONE);
		btnDisconnect.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnDisconnect.setText("Disconnect");
		
		hookListeners();
	}
	private void hookListeners() {
		btnNew.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				viewer.onNew();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		btnEdit.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				viewer.onEdit();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		btnDelete.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				viewer.onDelete();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		btnConnect.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				viewer.onConnect();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		btnDisconnect.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				viewer.onDiscconect();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}
	public void setEnabled(boolean enabled) {
		btnEdit.setEnabled(enabled);
		btnDelete.setEnabled(enabled);
		btnConnect.setEnabled(enabled);
		btnDisconnect.setEnabled(enabled);
	}
	public void setEditEnabled(boolean enabled) {
		btnEdit.setEnabled(enabled);
	}
	public void setDeleteEnabled(boolean enabled) {
		btnDelete.setEnabled(enabled);
	}
	public void setConnectEnabled(boolean enabled) {
		btnConnect.setEnabled(enabled);
	}
	public void setDisconnectEnabled(boolean enabled) {
		btnDisconnect.setEnabled(enabled);
	}
}
