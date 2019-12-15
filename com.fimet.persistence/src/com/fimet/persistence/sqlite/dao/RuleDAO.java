package com.fimet.persistence.sqlite.dao;

import java.sql.SQLException;
import java.util.List;

import com.fimet.commons.exception.DBException;
import com.fimet.core.entity.sqlite.Rule;
import com.fimet.persistence.sqlite.PersistenceException;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

public class RuleDAO extends AbstractDAO<Rule,Integer> {
	private static RuleDAO instance;
	public static RuleDAO getInstance() {
		if (instance == null) {
			instance = new RuleDAO();
		}
		return instance;
	}
	public RuleDAO() {
		super();
	}
	public RuleDAO(ConnectionSource connection) {
		super(connection);
	}
	public List<Rule> findByIdTypeAndField(int idTypeEnviroment, int idField){
		try {
			QueryBuilder<Rule, Integer> qb = getDAO().queryBuilder();
			qb.setWhere(qb.where().eq("idTypeEnviroment", idTypeEnviroment).and().eq("idField", idField));
			qb.orderBy("order", true);
			PreparedQuery<Rule> preparedQuery = qb.prepare();
			return getDAO().query(preparedQuery);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}
	public int deleteByIdEnviromentType(Integer id) {
		try {
			DeleteBuilder<Rule, Integer> db = getDAO().deleteBuilder();
			db.setWhere(db.where().eq("idTypeEnviroment", id));
			return db.delete();
		} catch (SQLException e) {
			throw new DBException("Cannot delete Rules",e);
		}
	}
}
