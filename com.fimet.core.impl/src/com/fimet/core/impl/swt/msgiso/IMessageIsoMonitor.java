package com.fimet.core.impl.swt.msgiso;

import com.fimet.core.impl.swt.msg.IMessageMonitor;

public interface IMessageIsoMonitor extends IMessageMonitor {
	public void onNew();
	public void onEdit();
	public void onDelete();
}