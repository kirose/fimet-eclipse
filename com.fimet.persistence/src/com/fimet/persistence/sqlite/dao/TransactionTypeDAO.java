package com.fimet.persistence.sqlite.dao;

import java.sql.SQLException;
import java.util.List;

import com.fimet.core.entity.sqlite.TransactionType;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

public class TransactionTypeDAO extends AbstractDAO<TransactionType,Integer> {
	private static TransactionTypeDAO instance;
	public static TransactionTypeDAO getInstance() {
		if (instance == null) {
			instance = new TransactionTypeDAO();
		}
		return instance;
	}
	public TransactionTypeDAO() {
		super();
	}
	public TransactionTypeDAO(ConnectionSource connection) {
		super(connection);
	}
	public List<TransactionType> findByIdDialect(Integer idDialect) {
		try {
			QueryBuilder<TransactionType, Integer> qb = getDAO().queryBuilder();
			qb.setWhere(qb.where().eq("idDialect", idDialect));
			return qb.query();
		} catch (SQLException e) {
			return null;
		}
	}
	public List<TransactionType> findRootsByIdDialect(Integer idDialect) {
		try {
			QueryBuilder<TransactionType, Integer> qb = getDAO().queryBuilder();
			qb.setWhere(qb.where().eq("idDialect", idDialect).and().isNull("idParent"));
			return qb.query();
		} catch (SQLException e) {
			return null;
		}
	}
	public List<TransactionType> findChildrenByIdDialect(Integer id, Integer idDialect) {
		try {
			QueryBuilder<TransactionType, Integer> qb = getDAO().queryBuilder();
			qb.setWhere(qb.where().eq("idDialect", idDialect).and().eq("idParent",id));
			return qb.query();
		} catch (SQLException e) {
			return null;
		}
	}
}