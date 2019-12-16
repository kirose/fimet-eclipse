package com.fimet.core.net;


import com.fimet.core.IManager;
import com.fimet.core.net.ISocket;
import com.fimet.core.net.listeners.IMessengerListener;
import com.fimet.core.net.listeners.IMessengerMonitor;

public interface IMessengerManager extends IManager {
	boolean isConnected(ISocket socket);
	boolean isDisconnected(ISocket socket);
	boolean isConnecting(ISocket socket);
	IMessenger getConnection(ISocket socket);
	IMessenger connect(ISocket socket);
	void disconnect(ISocket socket);
	void disconnectAll();
	void addMonitor(IMessengerMonitor monitor);
	void removeMonitor(IMessengerMonitor monitor);
	/**
	 * Remove the listener from all connections
	 * @param type
	 * @param listener
	 */
	void removeListener(int type, IMessengerListener listener);
	void setSocketTimeReconnect(int sec);
}
