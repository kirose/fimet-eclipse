package com.fimet.persistence.sqlite.dao;

import java.sql.SQLException;
import java.util.List;

import com.fimet.commons.exception.DBException;
import com.fimet.core.entity.sqlite.SimulatorMessage;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

public class SimulatorMessageDAO extends AbstractDAO<SimulatorMessage,Integer> {
	private static SimulatorMessageDAO instance;
	public static SimulatorMessageDAO getInstance() {
		if (instance == null) {
			instance = new SimulatorMessageDAO();
		}
		return instance;
	}
	public SimulatorMessageDAO() {
		super();
	}
	public SimulatorMessageDAO(ConnectionSource connection) {
		super(connection);
	}
	public SimulatorMessage findByMti(Integer idSimulator, String mti) {
		try {
			QueryBuilder<SimulatorMessage, Integer> qb = getDAO().queryBuilder();
			qb.setWhere(qb.where().eq("idSimulator", idSimulator).and().eq("mti", mti));
			qb.limit(1L);
			PreparedQuery<SimulatorMessage> preparedQuery = qb.prepare();
			return getDAO().queryForFirst(preparedQuery);
		} catch (SQLException e) {
			throw new DBException("Cannot find SimulatorMessage",e);
		}
	}
	public List<SimulatorMessage> findByIdSmulator(Integer idSimulator) {
		try {
			QueryBuilder<SimulatorMessage, Integer> qb = getDAO().queryBuilder();
			qb.setWhere(qb.where().eq("idSimulator", idSimulator));
			PreparedQuery<SimulatorMessage> preparedQuery = qb.prepare();
			return getDAO().query(preparedQuery);
		} catch (SQLException e) {
			throw new DBException("Cannot find SimulatorMessage",e);
		}
	}
	public int deleteByIdSimulator(Integer idSimulator) {
		try {
			DeleteBuilder<SimulatorMessage, Integer> db = getDAO().deleteBuilder();
			db.setWhere(db.where().eq("idSimulator", idSimulator));
			return db.delete();
		} catch (SQLException e) {
			throw new DBException("Cannot delete SimulatorMessage",e);
		}		
	}
}
