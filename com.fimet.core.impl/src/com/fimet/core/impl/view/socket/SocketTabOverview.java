package com.fimet.core.impl.view.socket;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.Text;

import com.fimet.commons.Color;
import com.fimet.commons.utils.StringUtils;
import com.fimet.core.impl.view.socket.SocketTabItem;
import com.fimet.core.net.ISocket;

public class SocketTabOverview extends SocketTabItem {

	private Text txtName;
	private Text txtStatus;
	private Text txtAddress;
	private Text txtPort;
	private Text txtProcess;
	private Text txtSocketType;
	private Text txtType;
	
	public SocketTabOverview(TabFolder folder) {
		super(folder);
        Composite composite = new Composite(folder, SWT.NONE);
        composite.setBackground(folder.getBackground());
		composite.setLayout(new GridLayout(2,false));
        composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,1,1));
        composite.setFont(folder.getFont());
        composite.setBackground(Color.WHITE);
        setControl(composite);
        setText("Overview");
		createContents(composite);
	}

	private void createContents(Composite composite) {

		Label label;
		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, true, false, 1, 1));
		label.setBackground(Color.WHITE);
		label.setText("Name:");
		
		txtName = new Text(composite, SWT.BORDER);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtName.setBackground(Color.WHITE);
		txtName.setEditable(false);

		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, true, false, 1, 1));
		label.setBackground(Color.WHITE);
		label.setText("Status:");
		
		txtStatus = new Text(composite, SWT.BORDER);
		txtStatus.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtStatus.setBackground(Color.WHITE);
		txtStatus.setEditable(false);		
		
		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, true, false, 1, 1));
		label.setBackground(Color.WHITE);
		label.setText("Address:");
		
		txtAddress = new Text(composite, SWT.BORDER);
		txtAddress.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtAddress.setBackground(Color.WHITE);
		txtAddress.setEditable(false);
		
		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, true, false, 1, 1));
		label.setBackground(Color.WHITE);
		label.setText("Port");
		
		txtPort = new Text(composite, SWT.BORDER);
		txtPort.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtPort.setBackground(Color.WHITE);
		txtPort.setEditable(false);

		
		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, true, false, 1, 1));
		label.setBackground(Color.WHITE);
		label.setText("Process:");
		
		txtProcess = new Text(composite, SWT.BORDER);
		txtProcess.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtProcess.setBackground(Color.WHITE);
		txtProcess.setEditable(false);

		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, true, false, 1, 1));
		label.setBackground(Color.WHITE);
		label.setText("Type:");
		
		txtType = new Text(composite, SWT.BORDER);
		txtType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtType.setBackground(Color.WHITE);
		txtType.setEditable(false);
		
		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, true, false, 1, 1));
		label.setBackground(Color.WHITE);
		label.setText("SocketType:");
		
		txtSocketType = new Text(composite, SWT.BORDER);
		txtSocketType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtSocketType.setBackground(Color.WHITE);
		txtSocketType.setEditable(false);
		
	}
	public void setSocket(ISocket socket) {
		txtName.setText(socket != null ? StringUtils.escapeNull(socket.getName()) : "");
		txtStatus.setText(socket != null ? (socket.isActive() ? "Active" : "Inactive") : "");
		txtAddress.setText(socket != null? StringUtils.escapeNull(socket.getAddress()) : "");
		txtPort.setText(socket != null ? StringUtils.escapeNull(socket.getPort()) : "");
		txtProcess.setText(socket != null ? StringUtils.escapeNull(socket.getProcess()) : "");
		txtSocketType.setText(socket != null ? (socket.isServer() ? "Server" : "Client") : "");
		txtType.setText(socket != null ? (socket.isAcquirer() ? "Acquirer" : "Issuer") : "");
	}
}
