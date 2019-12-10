package com.fimet.persistence.sqlite;

import java.io.IOException;
import java.sql.SQLException;

import org.eclipse.ui.PlatformUI;

import com.fimet.commons.console.Console;
import com.fimet.commons.exception.DBException;
import com.fimet.persistence.Activator;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public final class SQLiteManager implements ISQLiteManager {
	
	/*static {
		DataBaseManager.getInstanceSilently();
	}*/
	public static final String DB_NAME = "fimet.db";
	
	private static SQLiteManager instance;
	private ConnectionSource connection;

	public static SQLiteManager getInstance() {
		if (instance == null) {
			instance = new SQLiteManager();
			instance.init();
		}
		return instance;
	}
	public static SQLiteManager getInstanceSilently() {
		if (instance == null) {
			instance = new SQLiteManager();
		}
		return instance;
	}	
    public ConnectionSource getConnection() {
		return connection;
	}

	public SQLiteManager() {
		connect();
	}

	public void connect() {
    	try {
    		connection = new JdbcConnectionSource("jdbc:sqlite:plugins/com.fimet/persistence/"+DB_NAME);
		} catch (SQLException e) {
			disconnect();
		};
	}
	public void disconnect() {
		if (connection != null) {
			try {
				connection.close();
			} catch (IOException e) {
				Activator.getInstance().error("Cannot create ConnectionSource", e);
				throw new DBException(e);
			}
		}
	}
	private void init() {
		if (connection != null) {
			if (PlatformUI.isWorkbenchRunning()) {
				Console.getInstance().debug(SQLiteManager.class,"Connected to database "+DB_NAME);
			}
		}
	}
	@Override
	public void free() {}
	@Override
	public void saveState() {
		
	}
}
