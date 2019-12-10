package com.fimet.persistence.sqlite.dao;

import com.fimet.core.entity.sqlite.FieldMapper;
import com.j256.ormlite.support.ConnectionSource;

public class FieldMapperDAO extends AbstractDAO<FieldMapper,Integer> {
	private static FieldMapperDAO instance;
	public static FieldMapperDAO getInstance() {
		if (instance == null) {
			instance = new FieldMapperDAO();
		}
		return instance;
	}
	public FieldMapperDAO() {
		super();
	}
	public FieldMapperDAO(ConnectionSource connection) {
		super(connection);
	}
}
