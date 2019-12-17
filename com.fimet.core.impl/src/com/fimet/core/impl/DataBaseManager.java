
package com.fimet.core.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

import org.eclipse.ui.PlatformUI;

import com.fimet.commons.console.Console;
import com.fimet.commons.exception.DBException;
import com.fimet.core.IDataBaseManager;
import com.fimet.core.entity.sqlite.DataBase;
import com.fimet.core.impl.Activator;
import com.fimet.core.listener.IDataBaseConnected;
import com.fimet.core.listener.IDataBaseConnecting;
import com.fimet.core.listener.IDataBaseDeleted;
import com.fimet.core.listener.IDataBaseDisconnected;
import com.fimet.core.listener.IDataBaseInserted;
import com.fimet.core.listener.IDataBaseListener;
import com.fimet.core.listener.IDataBaseUpdated;
import com.fimet.persistence.sqlite.dao.DataBaseDAO;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class DataBaseManager implements IDataBaseManager {

	//Miliseconds
	private static long TIME_TO_ATTEMP_RECONNECT = 1000L * 60 * 5;// 5 Minutes

	private Date timeCheckConnection;
	private Connection connection;
	private DataBase dataBase;
	private LinkedBlockingDeque<Listener> listeners = new LinkedBlockingDeque<>();
	private List<DataBase> connections;

	public DataBaseManager() {
		connections = DataBaseDAO.getInstance().findAll();
	}
	public boolean isConnected() {
		return connection != null && dataBase != null;
	}
	public boolean isConnected(DataBase db) {
		return db != null && dataBase != null && dataBase.getName().equals(db.getName());
	}
	public void connect(DataBase db) {
		if (db == null) {
			throw new NullPointerException();
		}
		if (db.equals(dataBase)) {//No connect, actually connected
			return;
		}
		fireOnConnecting(db);
		try {
			getDriver();
		} catch (ClassNotFoundException ex) {
			fireOnDisconnected(db);
			throw new DBException("Driver not found", ex);
		}
		Connection connection = null;
		try {
			connection = getConnection(db);
		} catch (Throwable ex) {
			fireOnDisconnected(db);
			throw new DBException("Cannot create connection, invalid parameters",ex);
		}
		if (connection != null) {
			//Si fue exitosa la conexion desconectamos la conexion previa
			//DataBase temp = connected;
			disconnect();
			this.connection = connection;
			this.dataBase = db;
			fireOnConnected(db);
			timeCheckConnection = new Date(new Date().getTime() + TIME_TO_ATTEMP_RECONNECT);
			if (PlatformUI.isWorkbenchRunning() && Console.isEnabledDebug()) {
				Console.getInstance().debug(DataBaseManager.class, "Connected to database "+db.getName()+" ("+db.getAddress() + " " + db.getPort()+")");
			}
		}
	}
	protected Class<?> getDriver() throws ClassNotFoundException {
		throw new ClassNotFoundException("Diver not yet configured");
	}
	protected Connection getConnection(DataBase db) throws SQLException {
		throw new SQLException("Diver not yet configured");
	}

	public void disconnect() {
		try {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException ex) {} 
				if (PlatformUI.isWorkbenchRunning()) {
					Console.getInstance().debug(DataBaseManager.class,"Disconnected from database "+dataBase.getName()+" ("+dataBase.getAddress() + " " + dataBase.getPort()+")");
				}
				fireOnDisconnected(dataBase);
			}
		} catch (Exception | ThreadDeath e) {}
		finally {
			connection = null;
			dataBase = null;			
		}
	}
	public void disconnectSilenlty() {
		try {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException ex) {} 
				if (PlatformUI.isWorkbenchRunning()) {
					Console.getInstance().debug(DataBaseManager.class,"Disconnected from database "+dataBase.getName()+" ("+dataBase.getAddress() + " " + dataBase.getPort()+")");
				}
			}
		} catch (Exception | ThreadDeath e) {}
		finally {
			connection = null;
			dataBase = null;			
		}
	}
	public void testConnection(DataBase db) {
		if (db == null) {
			throw new NullPointerException();
		}
		try {
			getDriver();
		} catch (ClassNotFoundException ex) {
			throw new DBException("Driver not found",ex);
		}
		Connection connection = null;
		try {
			connection = getConnection(db);
		} catch (SQLException ex) {
			throw new DBException(ex);
		}
		try {
			Statement s = connection.createStatement();
			ResultSet rs = s.executeQuery("select * from dual");
			while(rs.next()) {
				rs.getString("DUMMY");
			}
		} catch (SQLException ex) {
			throw new DBException("Connection established, but cannot execute query.");
		}
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException ex) {}
	}
	public DataBase getDbConnection() {
		return dataBase;
	}
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		checkConnection();
		return connection.prepareStatement(sql);
	}
	public Statement createStatement() throws SQLException {
		checkConnection();
		return connection.createStatement();
	}
	private void checkConnection() throws SQLException {
		if (connection != null && dataBase != null && timeCheckConnection != null && new Date().after(timeCheckConnection)) {
			timeCheckConnection = new Date(new Date().getTime() + TIME_TO_ATTEMP_RECONNECT);
			try {
				Statement s = connection.createStatement();
				ResultSet rs = s.executeQuery("select * from dual");
				while(rs.next()) {
					rs.getString("DUMMY");
				}
			} catch (java.sql.SQLRecoverableException ex) {
				reconnect();//ThreadDeath
			}
		}
	}
	private void reconnect() {
		if (PlatformUI.isWorkbenchRunning()) {
			Console.getInstance().debug(DataBaseManager.class,"Reconnecting to database "+dataBase.getName()+" ("+dataBase.getAddress() + " " + dataBase.getPort()+")");
		}
		DataBase connection = dataBase;
		disconnectSilenlty();
		connect(connection);
	}
	@Override
	public void free() {
	}
	@Override
	public DataBase get(Integer id) {
		for (DataBase dataBase : connections) {
			if (dataBase.getId().equals(id)) {
				return dataBase;
			}
		}
		return null;
	}
	@Override
	public void disconnect(DataBase connection) {
		if (dataBase == connection) {
			disconnect();
		}
	}
	public DataBase getConnection(Integer id) {
		for (DataBase c : connections) {
			if (c.getId().equals(id)) {
				return c;
			}
		}
		return null;
	}
	@Override
	public DataBase getConnection(String name) {
		for (DataBase c : connections) {
			if (c.getName().equals(name)) {
				return c;
			}
		}
		return null;
	}
	public Connection getConnection() {
		return connection;
	}
	@Override
	public List<DataBase> getDataBases() {
		List<DataBase> list = new ArrayList<>(connections.size());
		for (DataBase dataBase : connections) {
			list.add(dataBase);
		}
		return list;
	}
	@Override
	public DataBase getActive() {
		return dataBase;
	}
	@Override
	public void saveState() {
		
	}
	@Override
	public DataBase insert(DataBase db) {
		if (db.getId() == null) {
			db.setId(getNextIdDataBase());
		}
		DataBaseDAO.getInstance().insert(db);
		connections.add(db);
		fireInserted(db);
		return db;
	}
	@Override
	public DataBase update(DataBase db) {
		DataBaseDAO.getInstance().update(db);
		fireUpdated(db);
		return db;
	}
	@Override
	public DataBase delete(DataBase db) {
		if (db != null && db.getId() != null) {
			DataBaseDAO.getInstance().delete(db);
			connections.remove(db);
			fireDeleted(db);
		}
		return db;
	}
	private class Listener {
		int type;
		IDataBaseListener listener;
		public Listener(int type, IDataBaseListener listener) {
			super();
			this.type = type;
			this.listener = listener;
		}
		public String toString() {
			return type+" "+listener;
		}
	}
	public void addFirstListener(int type, IDataBaseListener listener) {
		listeners.addFirst(new Listener(type, listener));
	}
	public void addListener(int type, IDataBaseListener listener) {
		listeners.add(new Listener(type, listener));
	}
	private Listener getListener(int type, IDataBaseListener listener) {
		for (Listener l : listeners) {
			if (l.type == type && l.listener == listener) {
				return l;
			}
		}
		return null;
	}
	public void removeListener(int type, IDataBaseListener listener) {
		Listener l = getListener(type, listener);
		if (l != null)
			listeners.remove(l);
	}
	private void fireInserted(DataBase e) {
		for (Listener l : listeners) {
			if (l.type == IDataBaseListener.ON_INSERT)
				try {((IDataBaseInserted)l.listener).onDataBaseInserted(e);}
				catch (Exception ex) {Activator.getInstance().warning("Error on inserted event: "+ex.getMessage(), ex);}
		}
	}
	private void fireDeleted(DataBase e) {
		for (Listener l : listeners) {
			if (l.type == IDataBaseListener.ON_DELETE)
				try {((IDataBaseDeleted)l.listener).onDataBaseDeleted(e);}
				catch (Exception ex) {Activator.getInstance().warning("Error on deleted event: "+ex.getMessage(), ex);}
		}
	}
	private void fireUpdated(DataBase e) {
		for (Listener l : listeners) {
			if (l.type == IDataBaseListener.ON_UPDATE)
				try {((IDataBaseUpdated)l.listener).onDataBaseUpdated(e);}
				catch (Exception ex) {Activator.getInstance().warning("Error on updated event: "+ex.getMessage(), ex);}
		}
	}
	private void fireOnDisconnected(DataBase e) {
		e.setStatusConnection(DataBase.DISCONNECTED);
		for (Listener l : listeners) {
			if (l.type == IDataBaseListener.ON_DISCONNECTED) {
				try {((IDataBaseDisconnected)l.listener).onDataBaseDisconnected(e);}
				catch (Exception ex) {Activator.getInstance().warning("Error on disconnected event: "+ex.getMessage(), ex);}
			}
		}
	}
	private void fireOnConnected(DataBase e) {
		e.setStatusConnection(DataBase.CONNECTED);
		for (Listener l : listeners) {
			if (l.type == IDataBaseListener.ON_CONNECTED) {
				try {((IDataBaseConnected)l.listener).onDataBaseConnected(e);}
				catch (Exception ex) {Activator.getInstance().warning("Error on connected event: "+ex.getMessage(), ex);}
			}
		}
	}
	private void fireOnConnecting(DataBase e) {
		e.setStatusConnection(DataBase.CONNECTING);
		for (Listener l : listeners) {
			if (l.type == IDataBaseListener.ON_CONNECTING) {
				try {((IDataBaseConnecting)l.listener).onDataBaseConnecting(e);}
				catch (Exception ex) {Activator.getInstance().warning("Error on connecting event: "+ex.getMessage(), ex);}
			}
		}
	}
	@Override
	public Integer getNextIdDataBase() {
		return DataBaseDAO.getInstance().getNextSequenceId();
	}
	@Override
	public Integer getPrevIdDataBase() {
		return DataBaseDAO.getInstance().getPrevSequenceId();
	}
}
