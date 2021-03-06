package com.fimet.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import com.fimet.commons.console.Console;
import com.fimet.commons.exception.SocketException;
import com.fimet.core.IPreferencesManager;
import com.fimet.core.Manager;
import com.fimet.core.ISO8583.adapter.IStreamAdapter;
import com.fimet.core.net.ISocketConnection;
import com.fimet.core.net.ISocketConnectionListener;
import com.fimet.core.net.ISocket;
/**
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public abstract class SocketConnection implements ISocketConnection {

	static final int DISCONNECTED = 0;
	static final int CONNECTING = 1;
	static final int CONNECTED = 2;
	
	public static final int SOCKET_CLIENT = 0;
	public static final int SOCKET_SERVER = 1;

	protected Socket socket;
	protected ISocket iSocket;
	private boolean reconnect = true;
	static int RECONNECTION_TIME = Manager.get(IPreferencesManager.class).getInt(IPreferencesManager.SOCKET_RECONNECT_TIME_SEC,2)*1000;// In Miliseconds
	private boolean sentDisconnected = false;
	private IStreamAdapter adapter;
	private ISocketConnectionListener listener;
	private int status;
	
	public SocketConnection(ISocket iSocket, ISocketConnectionListener listener) {
		super();
		if (listener == null) {
			throw new NullPointerException();
		}
		this.listener = listener;
		this.iSocket = iSocket;
		if (iSocket == null) {
			throw new NullPointerException();
		}
		if (iSocket.getAddress() == null) {
			throw new NullPointerException();
		} else if (!iSocket.getAddress().matches("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}")) {
			throw new SocketException("Invalid Address: " + iSocket.getAddress());
		}
		if (iSocket.getPort() == null) {
			throw new NullPointerException();
		} else if (iSocket.getPort() <= 0) {
			throw new SocketException("Invalid Port: " + iSocket.getPort());
		}
		adapter = (IStreamAdapter)iSocket.getAdapter();
		reconnect = listener.getSocketReconnect();
		status = DISCONNECTED;
	}
	public void connect() {
		if (isDisconnected()) new ThreadConnector().start();
	}
	abstract protected Socket newSocket() throws IOException;
	public void disconnect() {
		status = DISCONNECTED;
		sentDisconnected = true;
		close();
		listener.onSocketDisconnected();
	}
	abstract void close();
	public boolean isConncted() {
		return socket != null;
	}
	public void writeMessage(byte[] message) {
		writeMessage(message, true);
	}
	public void writeMessage(byte[] message, boolean adapt) {
		try {
			if (socket == null) {
				throw new SocketException("Socket is disconnected cannot write data");
			}
			if (adapt) {
				adapter.writeStream(socket.getOutputStream(), message);
			} else {
				socket.getOutputStream().write(message);
			}
			if (Console.isEnabledInfo()) {
				Console.getInstance().info(SocketConnection.class,"Written message ("+message.length+" bytes) to "+ iSocket);
			}
		} catch (IOException e) {
			Activator.getInstance().warning("Fail to write msg ("+message.length+" bytes) to "+ iSocket, e);
			close();
			listener.onSocketDisconnected();
			throw new SocketException(e);
		}
	}
	public void setAutoReconnect(boolean reconnect) {
		this.reconnect = reconnect;
	}
	public InputStream getInputStream() throws IOException {
		return socket.getInputStream();
	}
	public boolean isClient() {
		return true;
	}
	public synchronized boolean isDisconnected() {
		return status == DISCONNECTED;
	}
	public synchronized boolean isConnected() {
		return status == CONNECTED;
	}
	public synchronized boolean isConnecting() {
		return status == CONNECTING;
	}
	private class ThreadReader extends Thread {
		ThreadReader(){
			setName("Reader "+iSocket.toString());			
		}
		public void run() {
			try {
				while(!sentDisconnected){
					byte[] message = adapter.readStream(socket.getInputStream());
					if (Console.isEnabledInfo()) {
						Console.getInstance().info(SocketConnection.class,"Read message ("+(message != null ? message.length : null)+" bytes) from "+ iSocket);
					}
					if (message == null || message.length == 0) {
						//Console.getInstance().warning(SocketConnection.class, "Socket port ocuppied: "+iSocket);
						disconnect();
					} else {
						listener.onSocketRead(message);
					}
				}
			} catch (Exception e) {// Fallo la conexion
				//Activator.getInstance().error("Dissconected!!", e);
				close();// El servidor se desconecto no se puede leer del socket
				status = DISCONNECTED;
				listener.onSocketDisconnected();
				if (!sentDisconnected && reconnect) {
					new ThreadConnector().start();
				}
			}
		}
	}
	private class ThreadConnector extends Thread {
		ThreadConnector(){
			setName("Connector "+iSocket.toString());			
		}
		public void run() {
			status = CONNECTING;
			listener.onSocketConnecting();
			while (!sentDisconnected && socket == null) {
				try {
					socket = newSocket();
					status = CONNECTED;
					if (Console.isEnabledDebug()) Console.getInstance().debug(SocketConnection.class,"Connected to "+ iSocket);
					new ThreadReader().start();
					listener.onSocketConnected();
				} catch (Exception e) {
					socket = null;
				}
				if (RECONNECTION_TIME <= 0) {
					disconnect();
				} else {
					try {
						TimeUnit.MILLISECONDS.sleep(RECONNECTION_TIME);
					} catch (Exception e) {}
				}
			}
		}
	}
}
