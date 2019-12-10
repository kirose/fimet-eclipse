package com.fimet.core.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

import com.fimet.commons.console.Console;
import com.fimet.commons.exception.FimetException;
import com.fimet.core.IDataBaseManager;
import com.fimet.core.IEnviromentManager;
import com.fimet.core.IPreferencesManager;
import com.fimet.core.ISocketManager;
import com.fimet.core.Manager;
import com.fimet.core.entity.sqlite.Enviroment;
import com.fimet.core.entity.sqlite.EnviromentType;
import com.fimet.core.listener.IEnviromentConnected;
import com.fimet.core.listener.IEnviromentConnecting;
import com.fimet.core.listener.IEnviromentDeleted;
import com.fimet.core.listener.IEnviromentDisconnected;
import com.fimet.core.listener.IEnviromentInserted;
import com.fimet.core.listener.IEnviromentListener;
import com.fimet.core.listener.IEnviromentTypeDeleted;
import com.fimet.core.listener.IEnviromentTypeInserted;
import com.fimet.core.listener.IEnviromentTypeUpdated;
import com.fimet.core.listener.IEnviromentUpdated;
import com.fimet.core.net.IFtpManager;
import com.fimet.persistence.sqlite.dao.EnviromentDAO;
import com.fimet.persistence.sqlite.dao.EnviromentTypeDAO;
import com.fimet.persistence.sqlite.dao.RuleDAO;

public class EnviromentManager implements IEnviromentManager {

	private LinkedBlockingDeque<Listener> listeners = new LinkedBlockingDeque<>();
	private static IPreferencesManager preferencesManager = Manager.get(IPreferencesManager.class);
	private IDataBaseManager dataBaseManager = Manager.get(IDataBaseManager.class);
	private IFtpManager ftpManager = Manager.get(IFtpManager.class);
	private Enviroment active;
	private List<Enviroment> enviroments;
	private List<EnviromentType> types;
	
	public EnviromentManager() {
		types = EnviromentTypeDAO.getInstance().findAll();
		enviroments = EnviromentDAO.getInstance().findAll();
		for (Enviroment e : enviroments) {
			e.setType(getEnviromentType(e.getIdType()));
			if (e.getType() == null) {
				throw new FimetException("Cannot found type for enviroment "+e.getName());
			}
			if (dataBaseManager != null) {
				if (e.getIdDataBase() != null) {
					e.setDataBase(dataBaseManager.get(e.getIdDataBase()));
				} else {
					Activator.getInstance().error("Id DataBase Connection is null for enviroment "+e.getName());
				}
			}
			if (!e.getIsLocal()) {
				if (e.getIdFtp() != null) {
					e.setFtp(ftpManager.get(e.getIdFtp()));
				} else {
					Activator.getInstance().error("Id FTP Connection is null for enviroment "+e.getName());
				}
			}
		}
		autostart();
	}

	public void autostart() {
		Integer id = preferencesManager.getInt(IPreferencesManager.ENVIROMENT_AUTOSTART);
		if (id != null) {
			Enviroment enviroment = getEnviroment(id);
			if (enviroment != null) {
				start(enviroment);
			} else {
				Activator.getInstance().warning("Cannot found Enviroment for id "+id);	
			}
		} else {
			Activator.getInstance().info("There is not exists Enviroment configured for autostart");
		}
	}
	@Override
	public Enviroment getEnviroment(Integer id) {
		for (Enviroment e : enviroments) {
			if (e.getId().equals(id)) {
				return e;
			}
		}
		return null;
	}
	@Override
	public Enviroment getActive() {
		return active;
	}

	@Override
	public void start(Enviroment enviroment) {
		if (enviroment == null) {
			throw new NullPointerException("EnviromentType is null");
		}
		fireOnConnecting(enviroment);
		if (active != null) {
			stop(active);
		}
		if (!enviroment.getIsLocal() && enviroment.getFtp() != null) {
			try {
				ftpManager.connect(enviroment.getFtp());
			} catch (Exception e) {
				Console.getInstance().warning(EnviromentManager.class, "Invalid ftp connection "+enviroment.getFtp()+"");
				Activator.getInstance().error("Invalid ftp connection "+enviroment.getFtp()+"", e);
			}
		}
		if (dataBaseManager != null && enviroment.getDataBase() != null) {
			try {
				dataBaseManager.connect(enviroment.getDataBase());
				active = enviroment;
				fireOnConnected(enviroment);
			} catch (Exception e) {
				Console.getInstance().error(EnviromentManager.class, "Invalid data base connection "+enviroment.getDataBase()+"");
				Activator.getInstance().error("Invalid data base connection "+enviroment.getDataBase()+"", e);
				fireOnDisconnected(enviroment);
				active = null;
			}
		} else {
			active = enviroment;
			fireOnConnected(enviroment);
		}
	}
	@Override
	public void stop(Enviroment enviroment) {
		if (enviroment != null) {
			if (dataBaseManager != null) {
				dataBaseManager.disconnect(enviroment.getDataBase());
			}
			if (!enviroment.getIsLocal()) {
				ftpManager.disconnect(enviroment.getFtp());
			}
			fireOnDisconnected(enviroment);
			if (active == enviroment) {
				active = null;
			}
		}
	}

	@Override
	public List<Enviroment> getEnviroments() {
		if (enviroments == null) {
			return null;
		}
		List<Enviroment> list = new ArrayList<>(enviroments.size());
		for (Enviroment enviroment : enviroments) {
			list.add(enviroment);
		}
		return list;
	}

	public EnviromentType getEnviromentType(Integer id) {
		for (EnviromentType type : types) {
			if (type.getId().equals(id)) {
				return type;
			}
		}
		return null;
	}

	@Override
	public List<EnviromentType> getEnviromentsTypes() {
		if (types == null) {
			return null;
		}
		List<EnviromentType> list = new ArrayList<>();
		for (EnviromentType t : types) {
			list.add(t);
		}
		return list;
	}
	@Override
	public void free() {
	}
	@Override
	public void saveState() {
		for (Enviroment enviroment : enviroments) {
			EnviromentDAO.getInstance().insertOrUpdate(enviroment);
		}
		for (EnviromentType type : types) {
			EnviromentTypeDAO.getInstance().insertOrUpdate(type);
		}
	}


	@Override
	public Enviroment insert(Enviroment e) {
		if (e.getId() == null)
			e.setId(EnviromentDAO.getInstance().getNextSequenceId());
		EnviromentDAO.getInstance().insert(e);
		enviroments.add(e);
		fireInserted(e);
		return e;
	}

	@Override
	public Enviroment update(Enviroment e) {
		EnviromentDAO.getInstance().update(e);
		fireUpdated(e);
		return e;
	}
	@Override
	public Enviroment delete(Enviroment e) {
		Manager.get(ISocketManager.class).deleteByIdEnviroment(e.getId());
		EnviromentDAO.getInstance().delete(e);
		enviroments.remove(e);
		fireDeleted(e);
		return e;
	}
	@Override
	public EnviromentType insertEnviromentType(EnviromentType e) {
		if (e.getId() == null)
			e.setId(getNextIdEnviromentType());
		EnviromentTypeDAO.getInstance().insert(e);
		types.add(e);
		fireInsertedType(e);
		return e;
	}

	@Override
	public EnviromentType updateEnviromentType(EnviromentType e) {
		EnviromentTypeDAO.getInstance().update(e);
		fireUpdatedType(e);
		return e;
	}
	@Override
	public EnviromentType deleteEnviromentType(EnviromentType type) {
		List<Enviroment> remove = getEnviromentsByIdType(type.getId());
		for (Enviroment enviroment : remove) {
			delete(enviroment);
		}
		RuleDAO.getInstance().deleteByIdEnviromentType(type.getId());
		EnviromentTypeDAO.getInstance().delete(type);
		types.remove(type);
		fireDeletedType(type);
		return type;
	}

	@Override
	public Integer getNextIdEnviromentType() {
		return EnviromentTypeDAO.getInstance().getNextSequenceId();
	}
	@Override
	public Integer getPrevIdEnviromentType() {
		return EnviromentTypeDAO.getInstance().getPrevSequenceId();
	}
	@Override
	public List<Enviroment> getEnviromentsByIdType(Integer idType) {
		List<Enviroment> envs = new ArrayList<>();
		for (Enviroment e : enviroments) {
			if (e.getIdType().equals(idType)) {
				envs.add(e);
			}
		}
		return envs;
	}

	@Override
	public Integer getNextIdEnviroment() {
		return EnviromentDAO.getInstance().getNextSequenceId();
	}
	@Override
	public Integer getPrevIdEnviroment() {
		return EnviromentDAO.getInstance().getPrevSequenceId();
	}
	private class Listener {
		int type;
		IEnviromentListener listener;
		public Listener(int type, IEnviromentListener listener) {
			super();
			this.type = type;
			this.listener = listener;
		}
		public String toString() {
			return type+" "+listener;
		}
	}
	public void addFirstListener(int type, IEnviromentListener listener) {
		listeners.addFirst(new Listener(type, listener));
	}
	public void addListener(int type, IEnviromentListener listener) {
		listeners.add(new Listener(type, listener));
	}
	private Listener getListener(int type, IEnviromentListener listener) {
		for (Listener l : listeners) {
			if (l.type == type && l.listener == listener) {
				return l;
			}
		}
		return null;
	}
	public void removeListener(int type, IEnviromentListener listener) {
		Listener l = getListener(type, listener);
		if (l != null)
			listeners.remove(l);
	}
	private void fireInsertedType(EnviromentType e) {
		for (Listener l : listeners) {
			if (l.type == IEnviromentListener.ON_INSERT_TYPE)
				try {((IEnviromentTypeInserted)l.listener).onEnviromentTypeInserted(e);}
				catch (Exception ex) {Activator.getInstance().warning("Error on inserted event: "+active, ex);}
		}
	}
	private void fireDeletedType(EnviromentType e) {
		for (Listener l : listeners) {
			if (l.type == IEnviromentListener.ON_DELETE_TYPE)
				try {((IEnviromentTypeDeleted)l.listener).onEnviromentTypeDeleted(e);}
				catch (Exception ex) {Activator.getInstance().warning("Error on deleted event: "+active, ex);}
		}
	}
	private void fireUpdatedType(EnviromentType e) {
		for (Listener l : listeners) {
			if (l.type == IEnviromentListener.ON_UPDATE_TYPE)
				try {((IEnviromentTypeUpdated)l.listener).onEnviromentTypeUpdated(e);}
				catch (Exception ex) {Activator.getInstance().warning("Error on updated event: "+active, ex);}
		}
	}
	private void fireInserted(Enviroment e) {
		for (Listener l : listeners) {
			if (l.type == IEnviromentListener.ON_INSERT)
				try {((IEnviromentInserted)l.listener).onEnviromentInserted(e);}
				catch (Exception ex) {Activator.getInstance().warning("Error on inserted event: "+active, ex);}
		}
	}
	private void fireDeleted(Enviroment e) {
		for (Listener l : listeners) {
			if (l.type == IEnviromentListener.ON_DELETE)
				try {((IEnviromentDeleted)l.listener).onEnviromentDeleted(e);}
				catch (Exception ex) {Activator.getInstance().warning("Error on deleted event: "+active, ex);}
		}
	}
	private void fireUpdated(Enviroment e) {
		for (Listener l : listeners) {
			if (l.type == IEnviromentListener.ON_UPDATE)
				try {((IEnviromentUpdated)l.listener).onEnviromentUpdated(e);}
				catch (Exception ex) {Activator.getInstance().warning("Error on updated event: "+active, ex);}
		}
	}
	private void fireOnDisconnected(Enviroment e) {
		e.setStatusConnection(Enviroment.DISCONNECTED);
		for (Listener l : listeners) {
			if (l.type == IEnviromentListener.ON_DISCONNECTED) {
				try {((IEnviromentDisconnected)l.listener).onEnviromentDisconnected(e);}
				catch (Exception ex) {Activator.getInstance().warning("Error on disconnected event: "+active, ex);}
			}
		}
	}
	private void fireOnConnected(Enviroment e) {
		e.setStatusConnection(Enviroment.CONNECTED);
		for (Listener l : listeners) {
			if (l.type == IEnviromentListener.ON_CONNECTED) {
				try {((IEnviromentConnected)l.listener).onEnviromentConnected(e);}
				catch (Exception ex) {Activator.getInstance().warning("Error on connected event: "+active, ex);}
			}
		}
	}
	private void fireOnConnecting(Enviroment e) {
		e.setStatusConnection(Enviroment.CONNECTING);
		for (Listener l : listeners) {
			if (l.type == IEnviromentListener.ON_CONNECTING) {
				try {((IEnviromentConnecting)l.listener).onEnviromentConnecting(e);}
				catch (Exception ex) {Activator.getInstance().warning("Error on connecting event: "+active, ex);}
			}
		}
	}
}
