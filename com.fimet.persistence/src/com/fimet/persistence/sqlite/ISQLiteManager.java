package com.fimet.persistence.sqlite;

import com.fimet.core.IManager;
import com.j256.ormlite.support.ConnectionSource;

public interface ISQLiteManager extends IManager {
	public ConnectionSource getConnection();
	public void connect();
	public void disconnect();
}
