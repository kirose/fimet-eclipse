package com.fimet.persistence.sqlite.dao;

import java.sql.SQLException;
import java.util.List;

import com.fimet.core.entity.sqlite.ExtractorWindow;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

public class ExtractorWindowDAO extends AbstractDAO<ExtractorWindow,Integer> {
	private static ExtractorWindowDAO instance;
	public static ExtractorWindowDAO getInstance() {
		if (instance == null) {
			instance = new ExtractorWindowDAO();
		}
		return instance;
	}
	public ExtractorWindowDAO() {
		super();
	}
	public ExtractorWindowDAO(ConnectionSource connection) {
		super(connection);
	}
	public List<ExtractorWindow> findByIdTypeEnviroment(Integer idTypeEnviroment) {
		try {
			QueryBuilder<ExtractorWindow, Integer> qb = getDAO().queryBuilder();
			qb.setWhere(qb.where().eq("idTypeEnviroment", idTypeEnviroment));
			qb.orderBy("numberOfWindow", true);
			return qb.query();
		} catch (SQLException e) {
			return null;
		}
	}
}