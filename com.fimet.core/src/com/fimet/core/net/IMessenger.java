package com.fimet.core.net;

import com.fimet.core.ISO8583.parser.Message;
import com.fimet.core.net.ISocket;
import com.fimet.core.net.listeners.IMessengerListener;

public interface IMessenger {
	void wirteMessage(Message msg);
	void connect();
	void disconnect();
	ISocketConnection getSocket();
	ISocket getConnection();
	boolean isConnected();
	boolean isDisconnected();
	boolean isConnecting();
	void addListener(int type, IMessengerListener listener);
	void removeListener(int type, IMessengerListener listener);
}
