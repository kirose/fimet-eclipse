package com.fimet.persistence.sqlite.dao;

import java.sql.SQLException;
import java.util.List;

import com.fimet.commons.exception.DBException;
import com.fimet.core.entity.sqlite.Enviroment;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

public class EnviromentDAO extends AbstractDAO<Enviroment,Integer> {
	private static EnviromentDAO instance;
	public static EnviromentDAO getInstance() {
		if (instance == null) {
			instance = new EnviromentDAO();
		}
		return instance;
	}
	public EnviromentDAO() {
		super();
	}
	public EnviromentDAO(ConnectionSource connection) {
		super(connection);
	}
	public Enviroment findByName(String name) {
		try {
			QueryBuilder<Enviroment, Integer> qb = getDAO().queryBuilder();
			qb.setWhere(qb.where().eq("name", name));
			qb.limit(1L);
			return qb.queryForFirst();
		} catch (SQLException e) {
			return null;
		}
	}
	public int deleteByIdType(Integer id) {
		try {
			DeleteBuilder<Enviroment, Integer> db = getDAO().deleteBuilder();
			db.setWhere(db.where().eq("idType", id));
			return db.delete();
		} catch (SQLException e) {
			throw new DBException("Cannot delete Enviroments",e);
		}
	}
	public List<Enviroment> findByIdType(Integer idType) {
		try {
			QueryBuilder<Enviroment, Integer> qb = getDAO().queryBuilder();
			qb.setWhere(qb.where().eq("idType", idType));
			return qb.query();
		} catch (SQLException e) {
			return null;
		}
	}
}