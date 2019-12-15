package com.fimet.persistence.sqlite.dao;


import java.sql.SQLException;
import java.util.List;

import com.fimet.core.entity.sqlite.UseCaseReport;
import com.fimet.persistence.sqlite.PersistenceException;
import com.j256.ormlite.dao.RawRowMapper;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

public class UseCaseReportDAO extends AbstractDAO<UseCaseReport,String> {
	private static UseCaseReportDAO instance;
	public static UseCaseReportDAO getInstance() {
		if (instance == null) {
			instance = new UseCaseReportDAO();
		}
		return instance;
	}
	public UseCaseReportDAO() {
		super();
	}
	public UseCaseReportDAO(ConnectionSource connection) {
		super(connection);
	}
	public UseCaseReport findByPath(String path) {
		return findById(path);
	}
	public List<UseCaseReport> findByProject(String project) {
		try {
			QueryBuilder<UseCaseReport, String> qb = getDAO().queryBuilder();
			qb.setWhere(qb.where().eq("project", project));
			qb.orderBy("path", true);
			PreparedQuery<UseCaseReport> preparedQuery = qb.prepare();
			List<UseCaseReport> result = getDAO().query(preparedQuery);
			return result;
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}
	public List<UseCaseReport> findByIdJob(Long idJob) {
		try {
			QueryBuilder<UseCaseReport, String> qb = getDAO().queryBuilder();
			qb.setWhere(qb.where().eq("idJob", idJob));
			qb.orderBy("path", true);
			PreparedQuery<UseCaseReport> preparedQuery = qb.prepare();
			List<UseCaseReport> result = getDAO().query(preparedQuery);
			return result;
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}
	public List<String> findProjects() {
		try {
			List<String> projects = getDAO().queryRaw("select project from UseCaseReport group by project", new RawRowMapper<String>() {
			       @Override
			       public String mapRow(String[] columnNames, String[] resultColumns) throws SQLException {
			            return resultColumns[0];
			       }
			}).getResults();
			return projects;
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}
}