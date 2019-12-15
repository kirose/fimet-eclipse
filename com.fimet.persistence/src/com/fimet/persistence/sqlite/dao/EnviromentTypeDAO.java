package com.fimet.persistence.sqlite.dao;

import java.sql.SQLException;

import com.fimet.core.entity.sqlite.EnviromentType;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

public class EnviromentTypeDAO extends AbstractDAO<EnviromentType,Integer> {
	private static EnviromentTypeDAO instance;
	public static EnviromentTypeDAO getInstance() {
		if (instance == null) {
			instance = new EnviromentTypeDAO();
		}
		return instance;
	}
	public EnviromentTypeDAO() {
		super();
	}
	public EnviromentTypeDAO(ConnectionSource connection) {
		super(connection);
	}
	public EnviromentType findByName(String name) {
		try {
			QueryBuilder<EnviromentType, Integer> qb = getDAO().queryBuilder();
			qb.setWhere(qb.where().eq("name", name));
			qb.limit(1L);
			return qb.queryForFirst();
		} catch (SQLException e) {
			return null;
		}
	}
}