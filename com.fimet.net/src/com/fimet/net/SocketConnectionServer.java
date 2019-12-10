package com.fimet.net;


import java.io.IOException;

import java.net.Socket;

import com.fimet.core.net.ISocket;
import com.fimet.core.net.ISocketConnectionListener;
/**
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class SocketConnectionServer extends SocketConnection {

	protected java.net.ServerSocket serverSocket;
	
	public SocketConnectionServer(ISocket iSocket, ISocketConnectionListener listener) {
		super(iSocket, listener);
	}

	@Override
	protected Socket newSocket() throws IOException {
		serverSocket = new java.net.ServerSocket(iSocket.getPort());
		return serverSocket.accept();
	}
	@Override
	public boolean isClient() {
		return false;
	}
}
