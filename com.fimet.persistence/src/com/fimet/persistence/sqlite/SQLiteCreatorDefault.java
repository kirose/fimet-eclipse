package com.fimet.persistence.sqlite;

import java.sql.SQLException;

import com.fimet.persistence.sqlite.IDataBaseCreator;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class SQLiteCreatorDefault implements IDataBaseCreator {
	ConnectionSource connection;
	@Override
	public void create(ConnectionSource connection) throws SQLException {
		this.connection = connection;
		createTables();
	}
	private void createTables() throws SQLException {
		TableUtils.createTableIfNotExists(connection, com.fimet.core.entity.sqlite.UseCaseReport.class);
		TableUtils.createTableIfNotExists(connection, com.fimet.core.entity.sqlite.Socket.class);
		TableUtils.createTableIfNotExists(connection, com.fimet.core.entity.sqlite.EnviromentType.class);
		TableUtils.createTableIfNotExists(connection, com.fimet.core.entity.sqlite.Enviroment.class);
		TableUtils.createTableIfNotExists(connection, com.fimet.core.entity.sqlite.DataBase.class);
		TableUtils.createTableIfNotExists(connection, com.fimet.core.entity.sqlite.Ftp.class);
		TableUtils.createTableIfNotExists(connection, com.fimet.core.entity.sqlite.FieldFormatGroup.class);
		TableUtils.createTableIfNotExists(connection, com.fimet.core.entity.sqlite.FieldFormat.class);
		TableUtils.createTableIfNotExists(connection, com.fimet.core.entity.sqlite.FieldMapper.class);
		TableUtils.createTableIfNotExists(connection, com.fimet.core.entity.sqlite.Simulator.class);
		TableUtils.createTableIfNotExists(connection, com.fimet.core.entity.sqlite.SimulatorMessage.class);
		TableUtils.createTableIfNotExists(connection, com.fimet.core.entity.sqlite.Rule.class);
		TableUtils.createTableIfNotExists(connection, com.fimet.core.entity.sqlite.Parser.class);
		TableUtils.createTableIfNotExists(connection, com.fimet.core.entity.sqlite.MessageIso.class);
		TableUtils.createTableIfNotExists(connection, com.fimet.core.entity.sqlite.Preference.class);
	}
}
