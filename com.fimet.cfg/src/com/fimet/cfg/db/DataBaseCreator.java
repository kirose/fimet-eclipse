package com.fimet.cfg.db;


import java.sql.SQLException;
import java.util.List;

import com.fimet.commons.Version;
import com.fimet.core.entity.sqlite.DataBase;
import com.fimet.persistence.sqlite.dao.DataBaseDAO;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DataBaseCreator implements ICreator {
	DataBaseDAO dao;
	ConnectionSource connection;
	public DataBaseCreator(ConnectionSource connection) {
		super();
		this.connection = connection;
		dao = new DataBaseDAO(connection);
	}
	public DataBaseCreator create() throws SQLException {
		TableUtils.createTableIfNotExists(connection, DataBase.class);
		return this;
	}
	public DataBaseCreator drop() throws SQLException {
		TableUtils.dropTable(connection, DataBase.class, true);
		return this;
	}
	public DataBaseCreator insertAll() {

		dao.insertOrUpdate(new DataBase(0,"LOCAL BCMR",      "",          "orcl","localhost",   "1521","bcmr_pos_owner",Version.getInstance().encrypt("Bcmrp0s0wn3r21"), true));
		
		return this;
	}
	public DataBaseCreator showAll() {
		List<DataBase> all = dao.findAll();
		for (DataBase db : all) {
			System.out.println(db.getName()+","+db.getSid()+","+db.getSchema()+","+db.getAddress()+","+db.getPort()+","+db.getUser()+","+db.getPassword());
		}
		if (dao.count() > 0) {
			return this;
		}
		return this;
	}
}
