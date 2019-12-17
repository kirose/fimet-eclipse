package com.fimet.net;

import java.util.concurrent.ConcurrentLinkedQueue;
import com.fimet.core.ISO8583.parser.Message;
import com.fimet.core.net.ISocketConnection;
import com.fimet.core.net.ISocketConnectionListener;
import com.fimet.core.net.listeners.IMessengerListener;
import com.fimet.core.net.listeners.*;
import com.fimet.core.net.IMessenger;
import com.fimet.core.net.ISocket;

/**
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 * Messenger
 */
	
public abstract class Messenger implements IMessenger, ISocketConnectionListener {
	
	SocketConnection socket;
	ConcurrentLinkedQueue<Listener> listeners = new ConcurrentLinkedQueue<>();
	ISocket iSocket;
	int status;
	boolean reconnect = false;

	public Messenger(ISocket iSocket) {
		super();
		this.iSocket = iSocket;
	}
	private SocketConnection newSocketConnection() {
		if (iSocket.isServer()) {
			return socket = new SocketConnectionServer(iSocket, this);
		} else {
			return  socket = new SocketConnectionClient(iSocket, this);
		} 
	}
	abstract public void wirteMessage(Message message);

	/**
	 * Attempt to connect the iSocket
	 */
	public void connect() {
		if (isDisconnected()) {// Maybe socket is in CONNECTED or CONNECTING status
			newSocketConnection().connect();
		}
	}
	public void disconnect() {
		if (socket != null) {
			socket.disconnect();
			socket = null;
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
		return socket != null && socket.isConncted();
	}
	public synchronized boolean isDisconnected() {
		return socket == null || socket.isDisconnected();
	}
	public synchronized boolean isConnecting() {
		return socket != null && socket.isConnecting();
	}
	
	@Override
	public void onSocketConnecting() {
		fireOnConnecting();
	}
	@Override
	public void onSocketConnected() {
		fireOnConnect();
	}
	@Override
	public void onSocketDisconnected() {
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
				try{((IMessengerConnected)l.listener).onMessengerConnected(this);}
				catch(Throwable ex) {Activator.getInstance().warning("Error on connected listener: "+ex.getMessage(), ex);}
			}
		}
	}
	protected void fireOnDisconnect(){
		for (Listener l : listeners) {
			if (l.type == IMessengerListener.ON_DISCONNECTED) {
				try{ ((IMessengerDisconnected)l.listener).onMessengerDisconnected(this);}
				catch(Throwable ex) {Activator.getInstance().warning("Error on disconnected listener: "+ex.getMessage(), ex);}
			}
		}
	}
	protected void fireOnConnecting(){
		for (Listener l : listeners) {
			if (l.type == IMessengerListener.ON_CONNECTING) {
				try{((IMessengerConnecting)l.listener).onMessengerConnecting(this);}
				catch(Throwable ex) {Activator.getInstance().warning("Error on connecting listener: "+ex.getMessage(), ex);}
			}
		}
	}
	protected void fireOnComplete(){
		for (Listener l : listeners) {
			if (l.type == IMessengerListener.ON_COMPLETE) {
				try{((IMessengerComplete)l.listener).onMessengerComplete();}
				catch(Throwable ex) {Activator.getInstance().warning("Error on complete listener: "+ex.getMessage(), ex);}
			}
		}
	}
	protected void fireParseAcquirerResponse(Message message){
		for (Listener l : listeners) {
			if (l.type == IMessengerListener.ON_PARSE_ACQ_RESPONSE) {
				try{((IMessengerParseAcquirerResponse)l.listener).onMessengerParseAcquirerResponse(message);}
				catch(Throwable ex) {Activator.getInstance().warning("Error on parse aquirer response listener: "+ex.getMessage(), ex);}
			}
		}
	}
	protected void fireParseIssuerRequest(Message message){
		for (Listener l : listeners) {
			if (l.type == IMessengerListener.ON_PARSE_ISS_REQUEST) {
				try {((IMessengerParseIssuerRequest)l.listener).onMessengerParseIssuerRequest(message);}
				catch(Throwable ex) {Activator.getInstance().warning("Error on parse issuer request listener: "+ex.getMessage(), ex);}
			}
		}
	}
	protected void fireReadAcquirerResponse(byte[] message){
		for (Listener l : listeners) {
			if (l.type == IMessengerListener.ON_READ_ACQ_RESPONSE) {
				try {((IMessengerReadAcquirerResponse)l.listener).onMessengerReadAcquirerResponse(this, message);}
				catch(Throwable ex) {Activator.getInstance().warning("Error on read aquirer response listener: "+ex.getMessage(), ex);}
			}
		}
	}
	protected void fireReadIssuerRequest(byte[] message){
		for (Listener l : listeners) {
			if (l.type == IMessengerListener.ON_READ_ISS_REQUEST) {
				try {((IMessengerReadIssuerRequest)l.listener).onMessengerReadIssuerRequest(this, message);}
				catch(Throwable ex) {Activator.getInstance().warning("Error on read issuer request listener: "+ex.getMessage(), ex);}
			}
		}
	}
	protected Integer fireOnSimulateIssuerResponse(Message message){
		Integer delay = null;
		for (Listener l : listeners) {
			if (l.type == IMessengerListener.ON_SIMULATE_ISS_RESPONSE) {
				try {delay = ((IMessengerSimulateResponse)l.listener).onMessengerSimulateResponse(message);}
				catch(Throwable ex) {Activator.getInstance().warning("Error on simulate issuer response listener: "+ex.getMessage(), ex);}
			}
		}
		return delay;
	}
	protected void fireWriteAcquirerRequest(byte[] message){
		for (Listener l : listeners) {
			if (l.type == IMessengerListener.ON_WRITE_ACQ_REQUEST) {
				try {((IMessengerWriteAcquirerRequest)l.listener).onMessengerWriteAcquirerRequest(this, message);}
				catch(Throwable ex) {Activator.getInstance().warning("Error on write aquirer request listener: "+ex.getMessage(), ex);}
			}
		}
	}
	protected void fireWriteIssuerResponse(byte[] message){
		for (Listener l : listeners) {
			if (l.type == IMessengerListener.ON_WRITE_ISS_RESPONSE) {
				try {((IMessengerWriteIssuerResponse)l.listener).onMessengerWriteIssuerResponse(this, message);}
				catch(Throwable ex) {Activator.getInstance().warning("Error on write issuer response listener: "+ex.getMessage(), ex);}
			}
		}
	}
	public String toString() {
		return iSocket.toString();
	}
}
