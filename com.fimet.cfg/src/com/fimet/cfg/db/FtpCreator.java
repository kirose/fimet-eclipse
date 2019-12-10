package com.fimet.cfg.db;


import java.sql.SQLException;

import com.fimet.core.entity.sqlite.Ftp;
import com.fimet.persistence.sqlite.dao.FtpDAO;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class FtpCreator implements ICreator {
	FtpDAO dao;
	ConnectionSource connection;
	public FtpCreator(ConnectionSource connection) {
		super();
		this.connection = connection;
		dao = new FtpDAO(connection);
	}
	public FtpCreator create() throws SQLException {
		TableUtils.createTableIfNotExists(connection, Ftp.class);
		return this;
	}
	public FtpCreator drop() throws SQLException {
		TableUtils.dropTable(connection, Ftp.class, true);
		return this;
	}
	public FtpCreator insertAll() {

		dao.insertOrUpdate(new Ftp(0, "CANDY", "172.29.40.10", "22", "YourUser", "YourPassword"));
		dao.insertOrUpdate(new Ftp(1, "REMY", "172.29.40.6", "22", "YourUser", "YourPassword"));
		
		return this;
	}
}
