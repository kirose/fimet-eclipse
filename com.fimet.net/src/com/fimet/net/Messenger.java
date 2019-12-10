package com.fimet.net;

import java.util.concurrent.ConcurrentLinkedQueue;
import com.fimet.commons.console.Console;
import com.fimet.commons.exception.SocketException;
import com.fimet.commons.utils.ThreadUtils;
import com.fimet.core.Manager;
import com.fimet.core.ISO8583.parser.Message;
import com.fimet.core.net.ISocketConnection;
import com.fimet.core.net.ISocketConnectionListener;
import com.fimet.core.net.listeners.IMessengerListener;
import com.fimet.core.net.listeners.*;
import com.fimet.core.net.IMessenger;
import com.fimet.core.net.IMessengerManager;
import com.fimet.core.net.ISocket;

/**
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 * Messenger
 */
	
public abstract class Messenger implements IMessenger, ISocketConnectionListener {
	
	static final int DISCONNECTED = 0;
	static final int CONNECTING = 1;
	static final int CONNECTED = 2;
	
	SocketConnection socket;
	ConcurrentLinkedQueue<Listener> listeners = new ConcurrentLinkedQueue<>();
	ISocket iSocket;
	int status;
	boolean reconnect = false;

	public Messenger(ISocket iSocket) {
		super();
		this.iSocket = iSocket;
		if (iSocket.isServer()) {
			socket = new SocketConnectionServer(iSocket, this);
		} else {
			socket = new SocketConnectionClient(iSocket, this);
		}
		status = DISCONNECTED;
	}
	abstract public void wirteMessage(Message message);

	/**
	 * Attempt to connect the iSocket
	 */
	public void connect() {
		if (isDisconnected()) {
			if (!iSocket.isActive()) {
				Console.getInstance().error(Messenger.class, "Inactive socket "+iSocket+", select a socket Active.");
				throw new SocketException("Inactive socket: '"+iSocket+"' ");
			}
			checkSocket();
			ThreadUtils.runAcync(()->{
				try {
					socket.connect();
				} catch (IllegalThreadStateException e) {
					Manager.get(IMessengerManager.class).disconnect(iSocket);
					Manager.get(IMessengerManager.class).connect(iSocket);
				}
			});
		}
	}
	private void checkSocket() {
		if (socket instanceof SocketConnectionClient && !socket.isAlive()) {
			socket = new SocketConnectionClient(iSocket,this);
		}
	}
	public void disconnect() {
		if (!isDisconnected()) {
			socket.disconnect();
		}
	}
	public ISocketConnection getSocket() {
		return socket;
	}
	public void setSocket(SocketConnectionClient socket) {
		this.socket = socket;
	}
	@Override
	public ISocket getConnection() {
		return iSocket;
	}
	public synchronized boolean isConnected() {
		return status == CONNECTED;
	}
	public synchronized boolean isDisconnected() {
		return status == DISCONNECTED;
	}
	public synchronized boolean isConnecting() {
		return status == CONNECTING;
	}
	
	@Override
	public void onSocketConnecting() {
		status = CONNECTING;
		fireOnConnecting();
	}
	@Override
	public void onSocketConnected() {
		status = CONNECTED;
		fireOnConnect();
	}
	@Override
	public void onSocketDisconnected() {
		status = DISCONNECTED;
		fireOnDisconnect();
	}
	@Override
	public boolean getSocketReconnect() {
		return reconnect;		
	}
	private class Listener {
		int type;
		IMessengerListener listener;
		Listener(int type, IMessengerListener listener){
			this.type = type;
			this.listener = listener;
		}
		public String toString() {
			return type+" "+listener;
		}
	}
	public void addListener(int type, IMessengerListener listener) {
		if (getListener(type, listener) == null) listeners.add(new Listener(type, listener));
	}
	public void removeListener(int type, IMessengerListener listener) {
		Listener l = getListener(type, listener);
		if (l != null) {
			listeners.remove(l);
		}
	}
	private Listener getListener(int type, IMessengerListener listener) {
		for (Listener l : listeners) {
			if (l.type == type && l.listener == listener) {
				return l;
			}
		}
		return null;
	}
	protected void fireOnConnect(){
		for (Listener l : listeners) {
			if (l.type == IMessengerListener.ON_CONNECTED) {
				try{((IMessengerConnected)l.listener).onMessangerConnected(this);}
				catch(Exception ex) {Activator.getInstance().warning("Error on connected listener: "+ex.getMessage(), ex);}
			}
		}
	}
	protected void fireOnDisconnect(){
		for (Listener l : listeners) {
			if (l.type == IMessengerListener.ON_DISCONNECTED) {
				try{ ((IMessengerDisconnected)l.listener).onMessangerDisconnected(this);}
				catch(Exception ex) {Activator.getInstance().warning("Error on disconnected listener: "+ex.getMessage(), ex);}
			}
		}
	}
	protected void fireOnConnecting(){
		for (Listener l : listeners) {
			if (l.type == IMessengerListener.ON_CONNECTING) {
				try{((IMessengerConnecting)l.listener).onMessangerConnecting(this);}
				catch(Exception ex) {Activator.getInstance().warning("Error on connecting listener: "+ex.getMessage(), ex);}
			}
		}
	}
	protected void fireOnComplete(){
		for (Listener l : listeners) {
			if (l.type == IMessengerListener.ON_COMPLETE) {
				try{((IMessengerComplete)l.listener).onMessengerComplete();}
				catch(Exception ex) {Activator.getInstance().warning("Error on complete listener: "+ex.getMessage(), ex);}
			}
		}
	}
	protected void fireParseAcquirerResponse(Message message){
		for (Listener l : listeners) {
			if (l.type == IMessengerListener.ON_PARSE_ACQ_RESPONSE) {
				try{((IMessengerParseAcquirerResponse)l.listener).onMessangerParseAcquirerResponse(message);}
				catch(Exception ex) {Activator.getInstance().warning("Error on parse aquirer response listener: "+ex.getMessage(), ex);}
			}
		}
	}
	protected void fireParseIssuerRequest(Message message){
		for (Listener l : listeners) {
			if (l.type == IMessengerListener.ON_PARSE_ISS_REQUEST) {
				try {((IMessengerParseIssuerRequest)l.listener).onMessangerParseIssuerRequest(message);}
				catch(Exception ex) {Activator.getInstance().warning("Error on parse issuer request listener: "+ex.getMessage(), ex);}
			}
		}
	}
	protected void fireReadAcquirerResponse(byte[] message){
		for (Listener l : listeners) {
			if (l.type == IMessengerListener.ON_READ_ACQ_RESPONSE) {
				try {((IMessengerReadAcquirerResponse)l.listener).onMessangerReadAcquirerResponse(this, message);}
				catch(Exception ex) {Activator.getInstance().warning("Error on read aquirer response listener: "+ex.getMessage(), ex);}
			}
		}
	}
	protected void fireReadIssuerRequest(byte[] message){
		for (Listener l : listeners) {
			if (l.type == IMessengerListener.ON_READ_ISS_REQUEST) {
				try {((IMessengerReadIssuerRequest)l.listener).onMessangerReadIssuerRequest(this, message);}
				catch(Exception ex) {Activator.getInstance().warning("Error on read issuer request listener: "+ex.getMessage(), ex);}
			}
		}
	}
	protected Integer fireOnSimulateIssuerResponse(Message message){
		Integer delay = null;
		for (Listener l : listeners) {
			if (l.type == IMessengerListener.ON_SIMULATE_ISS_RESPONSE) {
				try {delay = ((IMessengerSimulateResponse)l.listener).onMessangerSimulateResponse(message);}
				catch(Exception ex) {Activator.getInstance().warning("Error on simulate issuer response listener: "+ex.getMessage(), ex);}
			}
		}
		return delay;
	}
	protected void fireWriteAcquirerRequest(byte[] message){
		for (Listener l : listeners) {
			if (l.type == IMessengerListener.ON_WRITE_ACQ_REQUEST) {
				try {((IMessengerWriteAcquirerRequest)l.listener).onMessangerWriteAcquirerRequest(this, message);}
				catch(Exception ex) {Activator.getInstance().warning("Error on write aquirer request listener: "+ex.getMessage(), ex);}
			}
		}
	}
	protected void fireWriteIssuerResponse(byte[] message){
		for (Listener l : listeners) {
			if (l.type == IMessengerListener.ON_WRITE_ISS_RESPONSE) {
				try {((IMessengerWriteIssuerResponse)l.listener).onMessangerWriteIssuerResponse(this, message);}
				catch(Exception ex) {Activator.getInstance().warning("Error on write issuer response listener: "+ex.getMessage(), ex);}
			}
		}
	}
	public String toString() {
		return iSocket.toString();
	}
}
