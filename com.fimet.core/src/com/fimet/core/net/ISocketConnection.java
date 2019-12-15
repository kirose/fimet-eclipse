package com.fimet.core.net;

import java.io.IOException;
import java.io.InputStream;


public interface ISocketConnection {
	void connect();
	void disconnect();
	void run();
	boolean isConncted();
	void writeMessage(byte[] message);
	void writeMessage(byte[] message, boolean adapt);
	void setAutoReconnect(boolean reconnect);
	InputStream getInputStream() throws IOException;
	boolean isClient();
}
