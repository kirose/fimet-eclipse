package com.fimet.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.fimet.core.entity.sqlite.DataBase;
import com.fimet.core.listener.IDataBaseListener;

public interface IDataBaseManager extends IManager {
	public DataBase insert(DataBase db);
	public DataBase update(DataBase db);
	public DataBase delete(DataBase db);
	public PreparedStatement prepareStatement(String sql) throws SQLException;
	public Statement createStatement() throws SQLException;
	public List<DataBase> getDataBases();
	public Connection getConnection();
	public DataBase getActive();
	public DataBase getConnection(Integer id);
	public DataBase getConnection(String name);
	public DataBase get(Integer id);
	public boolean isConnected();
	public boolean isConnected(DataBase db);
	public void connect(DataBase db);
	public void disconnect();
	public void disconnect(DataBase db);
	public void disconnectSilenlty();
	public void testConnection(DataBase db);
	public Integer getNextIdDataBase();
	public Integer getPrevIdDataBase();
	
	public void addFirstListener(int type, IDataBaseListener listener);
	public void addListener(int type, IDataBaseListener listener);
	public void removeListener(int type, IDataBaseListener listener);
}
