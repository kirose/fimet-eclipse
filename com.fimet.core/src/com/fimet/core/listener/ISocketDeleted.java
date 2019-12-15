package com.fimet.core.listener;

import com.fimet.core.net.ISocket;

public interface ISocketDeleted extends ISocketListener {
	public void onSocketDeleted(ISocket s);
}
