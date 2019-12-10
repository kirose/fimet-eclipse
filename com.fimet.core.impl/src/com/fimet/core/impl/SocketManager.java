package com.fimet.core.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.LinkedBlockingDeque;

import com.fimet.commons.utils.ReflectUtils;
import com.fimet.core.IEnviromentManager;
import com.fimet.core.IRuleManager;
import com.fimet.core.ISocketManager;
import com.fimet.core.ISocketFieldMapper;
import com.fimet.core.Manager;
import com.fimet.core.entity.sqlite.Enviroment;
import com.fimet.core.entity.sqlite.FieldMapper;
import com.fimet.core.entity.sqlite.Socket;
import com.fimet.core.impl.rule.Ruler;
import com.fimet.core.listener.IEnviromentConnected;
import com.fimet.core.listener.IEnviromentDisconnected;
import com.fimet.core.listener.ISocketDeleted;
import com.fimet.core.listener.ISocketInserted;
import com.fimet.core.listener.ISocketListener;
import com.fimet.core.listener.ISocketUpdateAll;
import com.fimet.core.listener.ISocketUpdated;
import com.fimet.core.net.ISocket;
import com.fimet.persistence.sqlite.dao.SocketDAO;

public class SocketManager implements ISocketManager, IEnviromentConnected, IEnviromentDisconnected {
	
	protected Map<String, List<ISocket>> socketByMachine = new HashMap<>();
	private Map<Integer, Ruler> rulers = new HashMap<Integer, Ruler>();
	private LinkedBlockingDeque<Listener> listeners = new LinkedBlockingDeque<>();
	public SocketManager() {
		Enviroment active = Manager.get(IEnviromentManager.class).getActive();
		if (active != null) {
			findByAddress(active.getAddress());
			refresh();
		}
		Manager.get(IEnviromentManager.class).addFirstListener(ON_DISCONNECTED, this);
		Manager.get(IEnviromentManager.class).addFirstListener(ON_CONNECTED, this);
	}
	@Override
	public void onEnviromentConnected(Enviroment e) {
		findByAddress(e.getAddress());
		refresh();
	}
	@Override
	public void onEnviromentDisconnected(Enviroment e) {
		socketByMachine.clear();
		rulers.clear();
	}
	@Override
	public void free() {
		
	}
	private int indexOf(List<ISocket> list, String name, String address, Integer port) {
		if (list != null && !list.isEmpty()) {
			int i = 0;
			for (ISocket sc : list) {
				if (sc.getName().equals(name) && sc.getAddress().equals(address) && sc.getPort().equals(port)) {
					return i;
				}
				i++;
			}
		}
		return -1;
	}
	@Override
	public ISocket find(String name, String address, Integer port) {
		if (socketByMachine.containsKey(address)) {
			int index = indexOf(socketByMachine.get(address), name, address, port);
			if (index < 0) {
				return null;
			} else {
				return socketByMachine.get(address).get(index);
			}
		}
		return null;
	}
	/*private boolean reallocate(ISocket socket) {
		String oldAddress = null;
		for (Entry<String, List<ISocket>> e : socketByMachine.entrySet()) {
			if (e.getValue().contains(socket)){
				oldAddress = e.getKey();
			}
		}
		if (oldAddress == null) {
			socketByMachine.get(socket.getAddress()).add(0, socket);
			return false;
		} else if (!oldAddress.equals(socket.getAddress())) {
			socketByMachine.get(oldAddress).remove(socket);
			socketByMachine.get(socket.getAddress()).add(0,socket);
			return true;
		}
		return false;
	}*/
	@Override
	public List<ISocket> findByAddress(String address) {
		if (socketByMachine.containsKey(address)) {
			return socketByMachine.get(address);
		}
		List<Socket> stored = SocketDAO.getInstance().findByAddress(address);
		List<ISocket> sockets = new ArrayList<ISocket>();
		for (Socket socket : stored) {
			sockets.add(socket);
		}
		socketByMachine.put(address, sockets);
		return sockets;
	}

	@Override
	public List<ISocket> findAcquirers(String address) {
		List<ISocket> sockets = findByAddress(address);
		List<ISocket> acquirers = new ArrayList<>();
		for (ISocket socket : sockets) {
			if (socket.isAcquirer()) {
				acquirers.add(socket);
			}
		}
		return acquirers;
	}

	@Override
	public List<ISocket> findIssuers(String address) {
		List<ISocket> sockets = findByAddress(address);
		List<ISocket> acquirers = new ArrayList<>();
		for (ISocket socket : sockets) {
			if (!socket.isAcquirer()) {
				acquirers.add(socket);
			}
		}
		return acquirers;
	}
	protected List<ISocket> configure(List<ISocket> connections) {
		List<FieldMapper> fieldMappers = Manager.get(IRuleManager.class).getFieldMappers();
		for (FieldMapper fieldMapper : fieldMappers) {
			configure(fieldMapper, connections);
		}
		return connections;
	}
	private List<ISocket> configure(FieldMapper fieldMapper, List<ISocket> connections) {
		try {
			if (!rulers.containsKey(fieldMapper.getId())) {
				Enviroment active = Manager.get(IEnviromentManager.class).getActive();
				if (active != null) {
					rulers.put(fieldMapper.getId(), new Ruler(active, fieldMapper.getId()));
				}
			}
			Ruler ruler =  rulers.get(fieldMapper.getId());
			Class<?> typeBinded = Class.forName(fieldMapper.getFieldClass());
			ISocketFieldMapper binder = (ISocketFieldMapper)Manager.get(Class.forName(fieldMapper.getMapperClass()));
			Method method = ReflectUtils.getSetterFromField(ISocket.class, fieldMapper.getField(), typeBinded);
			if (ruler == null) {
				for (ISocket connection : connections) {
					if (!(connection instanceof Socket)) {
						method.invoke(connection, new Object[] {null});
					}
				}
			} else {
				for (ISocket connection : connections) {
					if (!(connection instanceof Socket)) {
						Object binded = ruler != null ? binder.getBind(ruler.getIdResult(connection)) : null;
						method.invoke(connection, binded);
					}
				}	
			}
		} catch (Exception e) {
			Activator.getInstance().warning("Configuring field "+fieldMapper,e);
		}
		return connections;
	}

	@Override
	public void saveState() {
		
	}
	@Override
	public void refresh(List<Integer> idFields) {
		for (Integer id : idFields) {
			FieldMapper fieldMapper = Manager.get(IRuleManager.class).getFieldMapper(id);
			refresh(fieldMapper);
		}
		fireUpdateAll();
	}
	public void refresh(FieldMapper fieldMapper) {
		for(Map.Entry<String, List<ISocket>> e : socketByMachine.entrySet()) {
			configure(fieldMapper, e.getValue());
		}
	}
	public void refresh() {
		List<FieldMapper> fields = Manager.get(IRuleManager.class).getFieldMappers();
		for (FieldMapper field : fields) {
			for(Map.Entry<String, List<ISocket>> e : socketByMachine.entrySet()) {
				configure(field, e.getValue());
			}
		}
		fireUpdateAll();
	}

	@Override
	public void refresh(Integer idField) {
		FieldMapper fieldMapper = Manager.get(IRuleManager.class).getFieldMapper(idField);
		refresh(fieldMapper);
		fireUpdateAll();
	}

	@Override
	public List<Socket> findAllEntities() {
		return SocketDAO.getInstance().findAll();
	}
	private ISocket saveIntoCache(Socket socket) {
		if (!socketByMachine.containsKey(socket.getAddress())) {
			socketByMachine.put(socket.getAddress(),new ArrayList<>());
		}
		socketByMachine.get(socket.getAddress()).add(0,socket);
		return socket;
	}

	private void saveEntity(Socket socket) {
		SocketDAO.getInstance().insertOrUpdate(socket);
		ISocket find = find(socket.getName(), socket.getAddress(), socket.getPort());
		if (find != null) {
			if (find != socket)
				ReflectUtils.copy(socket, find);
			fireUpdated(find);
		} else {
			find = saveIntoCache(socket);
			fireInserted(find);
		}
	}
	private void deleteEntity(Socket socket) {
		SocketDAO.getInstance().delete(socket);
		ISocket find = find(socket.getName(), socket.getAddress(), socket.getPort());
		if (find != null && find instanceof Socket) {
			ReflectUtils.copy(socket, find);
			fireDeleted(find);
		}
	}

	@Override
	public Integer getNextIdSocket() {
		return SocketDAO.getInstance().getNextSequenceId();
	}
	@Override
	public Integer getPrevIdSocket() {
		return SocketDAO.getInstance().getPrevSequenceId();
	}

	@Override
	public void free(List<Integer> ids) {
	}

	@Override
	public void save(ISocket socket) {
		if (socket instanceof Socket) {
			saveEntity((Socket)socket);
		} else {
			ISocket find = find(socket.getName(), socket.getAddress(), socket.getPort());
			if (find != null) {
				if (find != socket)
					ReflectUtils.copy(socket, find);
				fireUpdated(find);
			}
		}
	}

	@Override
	public void delete(ISocket socket) {
		if (socket instanceof Socket) {
			deleteEntity((Socket)socket);
			fireDeleted((Socket)socket);
		} else {
			ISocket find = find(socket.getName(), socket.getAddress(), socket.getPort());
			if (find != null) {
				this.socketByMachine.get(find.getAddress()).remove(find);
				fireDeleted(find);
			}
		}
	}


	@Override
	public void deleteByIdEnviroment(Integer idEnviroment) {
		List<Socket> remove = new ArrayList<>();
		for (Entry<String, List<ISocket>> e : socketByMachine.entrySet()) {
			for (ISocket s : e.getValue()) {
				if (s instanceof Socket && idEnviroment == ((Socket)s).getEnviroment().getId())
					remove.add((Socket)s);
			}
		}
		for (Socket socket : remove) {
			deleteEntity(socket);
		}		
	}
	private class Listener {
		int type;
		ISocketListener listener;
		public Listener(int type, ISocketListener listener) {
			super();
			this.type = type;
			this.listener = listener;
		}
		public String toString() {
			return type+" "+listener;
		}
	}
	public void addFirstListener(int type, ISocketListener listener) {
		listeners.addFirst(new Listener(type, listener));
	}
	public void addListener(int type, ISocketListener listener) {
		listeners.add(new Listener(type, listener));
	}
	private Listener getListener(int type, ISocketListener listener) {
		for (Listener l : listeners) {
			if (l.type == type && l.listener == listener) {
				return l;
			}
		}
		return null;
	}
	public void removeListener(int type, ISocketListener listener) {
		Listener l = getListener(type, listener);
		if (l != null)
			listeners.remove(l);
	}
	private void fireInserted(ISocket e) {
		for (Listener l : listeners) {
			if (l.type == ISocketListener.ON_INSERT)
				try {((ISocketInserted)l.listener).onSocketInserted(e);}
				catch (Exception ex) {Activator.getInstance().warning("Error on inserted event: "+ex.getMessage(), ex);}
		}
	}
	private void fireDeleted(ISocket e) {
		for (Listener l : listeners) {
			if (l.type == ISocketListener.ON_DELETE)
				try {((ISocketDeleted)l.listener).onSocketDeleted(e);}
				catch (Exception ex) {Activator.getInstance().warning("Error on deleted event: "+ex.getMessage(), ex);}
		}
	}
	private void fireUpdated(ISocket e) {
		for (Listener l : listeners) {
			if (l.type == ISocketListener.ON_UPDATE)
				try {((ISocketUpdated)l.listener).onSocketUpdated(e);}
				catch (Exception ex) {Activator.getInstance().warning("Error on updated event: "+ex.getMessage(), ex);}
		}
	}
	private void fireUpdateAll() {
		for (Listener l : listeners) {
			if (l.type == ISocketListener.ON_UPDATE_ALL)
				try {((ISocketUpdateAll)l.listener).onSocketUpdateAll();}
				catch (Exception ex) {Activator.getInstance().warning("Error on update all event: "+ex.getMessage(), ex);}
		}
	}
}
