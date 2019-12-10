package com.fimet.cfg.db;


import java.io.IOException;
import java.sql.SQLException;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class Creator {
	ConnectionSource connection;
	public Creator() throws SQLException {
		connection = new JdbcConnectionSource("jdbc:sqlite:resources/fimet.db");
	}
	public Creator createDataBase() {
		try {

			TableUtils.createTableIfNotExists(connection, com.fimet.core.entity.sqlite.UseCaseReport.class);
			TableUtils.createTableIfNotExists(connection, com.fimet.core.entity.sqlite.Socket.class);
			
			new EnviromentCreator(connection).create();
			new DataBaseCreator(connection).create();
			new FtpCreator(connection).create();
			new FieldFormatCreator(connection).create();
			new SimulatorCreator(connection).create();
			new RulesCreator(connection).create().insertFieldMappers();
			new ParserCreator(connection).create();
			new MessageIsoCreator(connection).create();
			new FtpCreator(connection).create();
			new PreferencesCreator(connection).create().insertAll();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}
	public Creator close() throws IOException {
		if (connection != null)
			connection.close();
		return this;
	}
	public static void main(String[] args) throws Exception {
		new Creator()
		.createDataBase()
		.close();
	}
}
