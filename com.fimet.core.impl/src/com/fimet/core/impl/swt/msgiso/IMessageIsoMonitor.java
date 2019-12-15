package com.fimet.core.impl.swt.msgiso;

import com.fimet.core.impl.swt.msg.IMessageContainer;

public interface IMessageIsoMonitor extends IMessageContainer {
	public void onNew();
	public void onEdit();
	public void onDelete();
}