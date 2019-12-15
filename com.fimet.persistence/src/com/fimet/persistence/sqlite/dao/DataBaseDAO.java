package com.fimet.persistence.sqlite.dao;

import java.sql.SQLException;

import com.fimet.core.entity.sqlite.DataBase;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

public class DataBaseDAO extends AbstractDAO<DataBase,Integer> {
	private static DataBaseDAO instance;
	public static DataBaseDAO getInstance() {
		if (instance == null) {
			instance = new DataBaseDAO();
		}
		return instance;
	}
	public DataBaseDAO() {
		super();
	}
	public DataBaseDAO(ConnectionSource connection) {
		super(connection);
	}
	public DataBase findByName(String name) {
		try {
			QueryBuilder<DataBase, Integer> qb = getDAO().queryBuilder();
			qb.setWhere(qb.where().eq("name", name));
			qb.limit(1L);
			return qb.queryForFirst();
		} catch (SQLException e) {
			return null;
		}
	}
}