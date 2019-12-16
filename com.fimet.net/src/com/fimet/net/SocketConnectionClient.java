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
		return socket = new Socket(iSocket.getAddress(), iSocket.getPort());
	}
	@Override
	void close() {
		if (socket != null)  {
			try {
				socket.getInputStream().close();
			} catch (Throwable e) {/*Activator.getInstance().warning("socket.getInputStream().close()", e);*/}
			try {
				socket.getOutputStream().close();
			} catch (Throwable e) {/*Activator.getInstance().warning("socket.getOutputStream().close()", e);*/}
			try {
				socket.close();
			} catch (Throwable e) {/*Activator.getInstance().warning("socket.close()", e);*/}
			socket = null;
		}
	}
}
