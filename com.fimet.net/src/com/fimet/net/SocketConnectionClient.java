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
public class SocketConnectionClient extends SocketConnection {

	public SocketConnectionClient(ISocket iSocket, ISocketConnectionListener listener) {
		super(iSocket, listener);
	}
	@Override
	protected Socket newSocket() throws IOException {
		return new Socket(iSocket.getAddress(), iSocket.getPort());
	}
}
