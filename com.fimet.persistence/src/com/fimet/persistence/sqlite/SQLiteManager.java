package com.fimet.persistence.sqlite;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Bundle;

import com.fimet.commons.console.Console;
import com.fimet.commons.exception.DBException;
import com.fimet.commons.utils.PluginUtils;
import com.fimet.commons.utils.RuteUtils;
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
	public static final String CREATOR_EXTENSSION = "com.fimet.database.creator"; 
	/*static {
		DataBaseManager.getInstanceSilently();
	}*/
	public static final String DB_NAME = "fimet.db";
	
	private static SQLiteManager instance;
	private ConnectionSource connection;

	public static SQLiteManager getInstance() {
		if (instance == null) {
			instance = new SQLiteManager();
		}
		return instance;
	}
	public SQLiteManager() {
		createDataBaseIfNotExists();
		connect();
	}
    public ConnectionSource getConnection() {
		return connection;
	}
    private ConnectionSource newConnection() throws SQLException {
    	return new JdbcConnectionSource("jdbc:sqlite:plugins/com.fimet/persistence/"+DB_NAME);
    }
    private void createDataBaseIfNotExists() {
    	File fileDataBase = new File(RuteUtils.getPersistencePath()+DB_NAME);
    	if (!fileDataBase.exists()) {
			try {
				fileDataBase.createNewFile();
				connection = newConnection();
				findCreatorExtension().create(connection);;
			} catch (Exception e) {
				if (PlatformUI.isWorkbenchRunning()) {
					Console.getInstance().debug(SQLiteManager.class,"Erorr creating database "+DB_NAME);
				} else {
					e.printStackTrace(System.err);
				}
			}
    	}
    }
    private IDataBaseCreator findCreatorExtension() {
    	IConfigurationElement element = getCreatorConfigurationElement();
    	if (element != null) {
			Class<?> clazz;
			try {
				Bundle plugin = PluginUtils.startPlugin(element.getContributor().getName());
				clazz = plugin.loadClass(element.getAttribute("class"));
				if (IDataBaseCreator.class.isAssignableFrom(clazz)) {
					return (IDataBaseCreator)clazz.getConstructor().newInstance();
				}
			} catch (Exception ex) {
				Activator.getInstance().warning("Error in extension "+CREATOR_EXTENSSION,ex);
			}
			return new SQLiteCreatorDefault();
    	} else {
    		return new SQLiteCreatorDefault();
    	}
    }
	private IConfigurationElement getCreatorConfigurationElement() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elements = registry.getConfigurationElementsFor(CREATOR_EXTENSSION);
		if (elements != null && elements.length > 0) {
			for (IConfigurationElement e : elements) {
				if ("creator".equals(e.getName())) {
					return e;
				}
			}
		}
		return null;
	}
	public void connect() {
		if (connection == null) {
	    	try {
	    		connection = newConnection();
				if (PlatformUI.isWorkbenchRunning()) 
					Console.getInstance().debug(SQLiteManager.class,"Connected to database "+DB_NAME);
			} catch (SQLException e) {
				disconnect();
			}
		}
	}
	public void disconnect() {
		if (connection != null) {
			try {
				connection.close();
			} catch (IOException e) {
				if (PlatformUI.isWorkbenchRunning()) {
					Activator.getInstance().error("Cannot create Connection to "+DB_NAME, e);
				}
				throw new DBException(e);
			}
		}
	}
	@Override
	public void free() {}
	@Override
	public void saveState() {
		
	}
}
