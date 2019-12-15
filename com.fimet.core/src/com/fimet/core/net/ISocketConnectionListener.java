package com.fimet.core.net;

public interface ISocketConnectionListener {
	void onSocketRead(byte[] bytes);
	void onSocketDisconnected();
	void onSocketConnecting();
	void onSocketConnected();
	boolean getSocketReconnect();
	//ISocket getConnection();
}