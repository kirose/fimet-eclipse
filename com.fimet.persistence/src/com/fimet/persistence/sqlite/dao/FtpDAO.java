package com.fimet.persistence.sqlite.dao;


import java.sql.SQLException;

import com.fimet.core.entity.sqlite.Ftp;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

public class FtpDAO extends AbstractDAO<Ftp,Integer> {
	private static FtpDAO instance;
	public static FtpDAO getInstance() {
		if (instance == null) {
			instance = new FtpDAO();
		}
		return instance;
	}
	public FtpDAO() {
		super();
	}
	public FtpDAO(ConnectionSource connection) {
		super(connection);
	}
	public Ftp findByName(String name) {
		try {
			QueryBuilder<Ftp, Integer> qb = getDAO().queryBuilder();
			qb.setWhere(qb.where().eq("name", name));
			qb.limit(1L);
			return qb.queryForFirst();
		} catch (SQLException e) {
			return null;
		}
	}
}