package com.fimet.persistence.sqlite.dao;

import java.sql.SQLException;
import java.util.List;

import com.fimet.core.entity.sqlite.Simulator;
import com.fimet.persistence.sqlite.PersistenceException;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

public class SimulatorDAO extends AbstractDAO<Simulator,Integer> {
	private static SimulatorDAO instance;
	public static SimulatorDAO getInstance() {
		if (instance == null) {
			instance = new SimulatorDAO();
		}
		return instance;
	}
	public SimulatorDAO() {
		super();
	}
	public SimulatorDAO(ConnectionSource connection) {
		super(connection);
	}
	public List<Simulator> findAllIssuers() {
		try {
			QueryBuilder<Simulator,Integer> qb = getDAO().queryBuilder();
			qb.setWhere(qb.where().eq("type", Simulator.ISSUER));
			PreparedQuery<Simulator> preparedQuery = qb.prepare();
			return getDAO().query(preparedQuery);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}	
	}
	public List<Simulator> findAllAcquirers() {
		try {
			QueryBuilder<Simulator,Integer> qb = getDAO().queryBuilder();
			qb.setWhere(qb.where().eq("type", Simulator.ACQUIRER));
			PreparedQuery<Simulator> preparedQuery = qb.prepare();
			return getDAO().query(preparedQuery);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}	
	}
	public Simulator findByName(String name) {
		try {
			QueryBuilder<Simulator, Integer> qb = getDAO().queryBuilder();
			qb.setWhere(qb.where().eq("name", name));
			qb.limit(1L);
			return qb.queryForFirst();
		} catch (SQLException e) {
			return null;
		}
	}
	public Simulator findByNameAndType(String name, char type) {
		try {
			QueryBuilder<Simulator, Integer> qb = getDAO().queryBuilder();
			qb.setWhere(qb.where().eq("name", name).and().eq("type", type));
			qb.limit(1L);
			return qb.queryForFirst();
		} catch (SQLException e) {
			return null;
		}
	}
}
