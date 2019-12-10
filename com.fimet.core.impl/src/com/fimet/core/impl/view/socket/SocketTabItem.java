package com.fimet.core.impl.view.socket;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import com.fimet.core.net.ISocket;

public abstract class SocketTabItem extends TabItem {
	private boolean loaded;
	public SocketTabItem(TabFolder parent) {
		super(parent, SWT.NONE);
	}
	public boolean isLoaded() {
		return loaded;
	}
	public void setIsLoaded(boolean loaded) {
		this.loaded = loaded;
	}
	abstract public void setSocket(ISocket socket);
	@Override
	protected void checkSubclass () {}
}
