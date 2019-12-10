package com.fimet.core.impl.swt.msg;

import org.eclipse.jface.viewers.ISelectionProvider;

import com.fimet.core.net.ISocket;

public interface IMessageMonitor {
	void onModifyMessage();
	ISocket getConnection();
	ISelectionProvider getSelectionProvider();
}
