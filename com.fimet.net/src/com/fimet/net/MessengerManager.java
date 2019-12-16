package com.fimet.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fimet.core.IEnviromentManager;
import com.fimet.core.Manager;
import com.fimet.core.entity.sqlite.Enviroment;
import com.fimet.core.listener.IEnviromentDisconnected;
import com.fimet.core.net.IMessenger;
import com.fimet.core.net.IMessengerManager;
import com.fimet.core.net.ISocket;
import com.fimet.core.net.listeners.IMessengerMonitor;
import com.fimet.core.net.listeners.IMessengerListener;

/**
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
	
public class MessengerManager implements IMessengerManager, IEnviromentDisconnected {
	
	private Map<ISocket, IMessenger> connections = new HashMap<>();
	private List<IMessengerMonitor> monitors = new ArrayList<>();
	public MessengerManager() {
		Manager.get(IEnviromentManager.class).addListener(ON_DISCONNECTED, this);
	}
	@Override
	public void onEnviromentDisconnected(Enviroment e) {
		disconnectAll();
	}
	@Override
	public boolean isConnected(ISocket socket) {
		return connections.containsKey(socket) && connections.get(socket).isConnected();
	}
	@Override
	public boolean isDisconnected(ISocket socket) {
		return !connections.containsKey(socket) || connections.get(socket).isDisconnected();
	}
	@Override
	public boolean isConnecting(ISocket socket) {
		return connections.containsKey(socket) && connections.get(socket).isConnecting();
	}
	@Override
	public IMessenger getConnection(ISocket socket) {
		if (!connections.containsKey(socket)) {
			Messenger connection;
			if (socket.isAcquirer()) {
				connections.put(socket, connection = new MessengerAcquirer(socket));	
			} else {
				connections.put(socket, connection = new MessengerIssuer(socket));
			}
			addMonitorListeners(connection);
		}
		return connections.get(socket);
	}
	@Override
	public IMessenger connect(ISocket socket) {
		IMessenger ctrl = getConnection(socket);
		ctrl.connect();
		return ctrl;
	}
	@Override
	public void disconnect(ISocket socket) {
		if (connections.containsKey(socket)) {
			connections.get(socket).disconnect();
			connections.remove(socket);
		}
	}
	@Override
	public void disconnectAll() {
		for (Map.Entry<ISocket, IMessenger> e : connections.entrySet()) {
			e.getValue().disconnect();
		}
		connections.clear();
	}
	@Override
	public void free() {}
	@Override
	public void saveState() {}
	@Override
	public void addMonitor(IMessengerMonitor monitor) {
		monitors.add(monitor);
		for (Map.Entry<ISocket, IMessenger> e : connections.entrySet()) {
			e.getValue().addListener(IMessengerListener.ON_CONNECTED, monitor);
			e.getValue().addListener(IMessengerListener.ON_CONNECTING, monitor);
			e.getValue().addListener(IMessengerListener.ON_DISCONNECTED, monitor);
		}
	}
	@Override
	public void removeMonitor(IMessengerMonitor monitor) {
		monitors.remove(monitor);
		for (Map.Entry<ISocket, IMessenger> e : connections.entrySet()) {
			e.getValue().removeListener(IMessengerListener.ON_CONNECTED, monitor);
			e.getValue().removeListener(IMessengerListener.ON_CONNECTING, monitor);
			e.getValue().removeListener(IMessengerListener.ON_DISCONNECTED, monitor);
		}
	}
	private void addMonitorListeners(IMessenger connection) {
		for (IMessengerMonitor monitor : monitors) {
			connection.addListener(IMessengerListener.ON_CONNECTED, monitor);
			connection.addListener(IMessengerListener.ON_CONNECTING, monitor);
			connection.addListener(IMessengerListener.ON_DISCONNECTED, monitor);
		}
	}
	@Override
	public void removeListener(int type, IMessengerListener listener) {
		if (!connections.isEmpty())
			for (Map.Entry<ISocket, IMessenger> e : connections.entrySet())
				e.getValue().removeListener(type, listener);
	}
	public void setSocketTimeReconnect(int sec) {
		SocketConnection.RECONNECTION_TIME = sec*1000;
	}
}
