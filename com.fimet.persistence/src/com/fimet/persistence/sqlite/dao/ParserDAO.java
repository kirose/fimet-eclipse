package com.fimet.persistence.sqlite.dao;

import java.sql.SQLException;
import java.util.List;

import com.fimet.core.entity.sqlite.Parser;
import com.fimet.persistence.sqlite.PersistenceException;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

public class ParserDAO extends AbstractDAO<Parser,Integer> {
	private static ParserDAO instance;
	public static ParserDAO getInstance() {
		if (instance == null) {
			instance = new ParserDAO();
		}
		return instance;
	}
	public ParserDAO() {
		super();
	}
	public ParserDAO(ConnectionSource connection) {
		super(connection);
	}
	public Parser findByName(String name){
		try {
			QueryBuilder<Parser, Integer> qb = getDAO().queryBuilder();
			qb.setWhere(qb.where().eq("name", name));
			qb.limit(1L);
			return getDAO().queryForFirst(qb.prepare());
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}
	public List<Parser> findByType(int type){
		try {
			QueryBuilder<Parser, Integer> qb = getDAO().queryBuilder();
			qb.setWhere(qb.where().eq("type", type));
			return getDAO().query(qb.prepare());
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

}
