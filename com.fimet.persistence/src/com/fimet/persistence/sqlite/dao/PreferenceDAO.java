package com.fimet.persistence.sqlite.dao;

import com.fimet.core.entity.sqlite.Preference;
import com.j256.ormlite.support.ConnectionSource;

public class PreferenceDAO extends AbstractDAO<Preference,String> {
	
	private static PreferenceDAO instance;
	public static PreferenceDAO getInstance() {
		if (instance == null) {
			instance = new PreferenceDAO();
		}
		return instance;
	}
	public PreferenceDAO() {
		super();
	}
	public PreferenceDAO(ConnectionSource connection) {
		super(connection);
	}
}
