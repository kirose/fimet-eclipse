package com.fimet.core.listener;

import com.fimet.core.net.ISocket;

public interface ISocketInserted extends ISocketListener {
	public void onSocketInserted(ISocket s);
}
