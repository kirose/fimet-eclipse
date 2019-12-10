package com.fimet.cfg.db;


import java.sql.SQLException;
import java.util.List;

import com.fimet.core.IPreferencesManager;
import com.fimet.core.entity.sqlite.Preference;
import com.fimet.persistence.sqlite.dao.PreferenceDAO;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class PreferencesCreator implements ICreator {
	PreferenceDAO dao;
	ConnectionSource connection;
	public PreferencesCreator(ConnectionSource connection) {
		super();
		this.connection = connection;
		dao = new PreferenceDAO(connection);
	}
	public PreferencesCreator create() throws SQLException {
		TableUtils.createTableIfNotExists(connection, Preference.class);
		return this;
	}
	public PreferencesCreator drop() throws SQLException {
		TableUtils.dropTable(connection, Preference.class, true);
		return this;
	}
	public PreferencesCreator insertAll() {

		dao.insertOrUpdate(new Preference(IPreferencesManager.CONSOLE_LEVEL, "" +(com.fimet.commons.console.Console.INFO | com.fimet.commons.console.Console.WARNING | com.fimet.commons.console.Console.ERROR | com.fimet.commons.console.Console.DEBUG)));
		dao.insertOrUpdate(new Preference(IPreferencesManager.LOG_ENABLE,"true"));
		dao.insertOrUpdate(new Preference(IPreferencesManager.LOG_GARBAGE_CYCLE_TIME_SEC,"14"));
		dao.insertOrUpdate(new Preference(IPreferencesManager.LOG_READER_CYCLE_TIME_SEC,"4"));
		dao.insertOrUpdate(new Preference(IPreferencesManager.UPDATE_DATES_ON_FORMAT,"true"));
		dao.insertOrUpdate(new Preference(IPreferencesManager.UPDATE_DATES_ON_PARSE,"false"));
		dao.insertOrUpdate(new Preference(IPreferencesManager.VALIDATE_TYPES_FIELD_FORMAT,"true"));
		dao.insertOrUpdate(new Preference(IPreferencesManager.CREATE_SIMQUEUE_READ_ACQUIRER, "true"));
		dao.insertOrUpdate(new Preference(IPreferencesManager.CREATE_SIMQUEUE_WRITE_ACQUIRER, "true"));
		dao.insertOrUpdate(new Preference(IPreferencesManager.CREATE_SIMQUEUE_READ_ISSUER, "true"));
		dao.insertOrUpdate(new Preference(IPreferencesManager.CREATE_SIMQUEUE_WRITE_ISSUER, "true"));
		dao.insertOrUpdate(new Preference(IPreferencesManager.TIME_EXCECUTION_USE_CASE, "65000"));
		dao.insertOrUpdate(new Preference(IPreferencesManager.EXTRACTOR_GARBAGE_CYCLE_TIME_SEC, "60"));
		dao.insertOrUpdate(new Preference(IPreferencesManager.EXTRACTOR_READER_CYCLE_TIME_SEC, "5"));
		return this;
	}
	public PreferencesCreator showAll() {
		List<Preference> all = dao.findAll();
		for (Preference db : all) {
			System.out.println(db.toString());
		}
		if (dao.count() > 0) {
			return this;
		}
		return this;
	}
}
