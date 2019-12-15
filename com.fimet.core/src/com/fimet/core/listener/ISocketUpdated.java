package com.fimet.core.listener;

import com.fimet.core.net.ISocket;

public interface ISocketUpdated extends ISocketListener {
	public void onSocketUpdated(ISocket s);
}
