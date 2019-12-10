package com.fimet.persistence.sqlite.dao;

import java.sql.SQLException;
import java.util.List;

import com.fimet.core.entity.sqlite.FieldFormatGroup;
import com.fimet.persistence.sqlite.PersistenceException;
import com.j256.ormlite.dao.RawRowMapper;
import com.j256.ormlite.support.ConnectionSource;

public final class FieldFormatGroupDAO extends AbstractDAO<FieldFormatGroup,Integer> {
	private static FieldFormatGroupDAO instance;
	public static FieldFormatGroupDAO getInstance() {
		if (instance == null) {
			instance = new FieldFormatGroupDAO();
		}
		return instance;
	}
	public FieldFormatGroupDAO() {
		super();
	}
	public FieldFormatGroupDAO(ConnectionSource connection) {
		super(connection);
	}
	public List<Integer> findRootIds() {
		try {
			return getDAO().queryRaw("select id from FieldFormatGroup where idParent is null order by id asc", new RawRowMapper<Integer>() {
			       @Override
			       public Integer mapRow(String[] columnNames, String[] resultColumns) throws SQLException {
			            return resultColumns[0] != null ? Integer.parseInt(resultColumns[0]) : null;
			       }
			}).getResults();
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}	
	}
	public List<Integer> findAllIds() {
		try {
			return getDAO().queryRaw("select id from FieldFormatGroup order by id asc", new RawRowMapper<Integer>() {
			       @Override
			       public Integer mapRow(String[] columnNames, String[] resultColumns) throws SQLException {
			            return resultColumns[0] != null ? Integer.parseInt(resultColumns[0]) : null;
			       }
			}).getResults();
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}	
	}
	public List<Integer> findChildIds(Integer idGroup) {
		try {
			return getDAO().queryRaw("select id from FieldFormatGroup where idParent = "+idGroup+" order by id asc", new RawRowMapper<Integer>() {
			       @Override
			       public Integer mapRow(String[] columnNames, String[] resultColumns) throws SQLException {
			            return resultColumns[0] != null ? Integer.parseInt(resultColumns[0]) : null;
			       }
			}).getResults();
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}	
	}
}
