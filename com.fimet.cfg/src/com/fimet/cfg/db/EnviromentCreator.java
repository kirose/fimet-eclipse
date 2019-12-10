package com.fimet.cfg.db;



import java.sql.SQLException;
import java.util.List;

import com.fimet.core.entity.sqlite.Enviroment;
import com.fimet.core.entity.sqlite.EnviromentType;
import com.fimet.persistence.sqlite.dao.EnviromentDAO;
import com.fimet.persistence.sqlite.dao.EnviromentTypeDAO;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class EnviromentCreator implements ICreator {
	
	EnviromentTypeDAO daoType;
	EnviromentDAO daoEnv;
	ConnectionSource connection;
	public EnviromentCreator(ConnectionSource connection) {
		super();
		this.connection = connection;
		daoType = new EnviromentTypeDAO(connection);
		daoEnv = new EnviromentDAO(connection);
	}
	public EnviromentCreator create() throws SQLException {
		TableUtils.createTableIfNotExists(connection, EnviromentType.class);
		TableUtils.createTableIfNotExists(connection, Enviroment.class);
		return this;
	}
	public EnviromentCreator drop() throws SQLException {
		TableUtils.dropTable(connection, EnviromentType.class, true);
		TableUtils.dropTable(connection, Enviroment.class, true);
		return this;
	}
	public EnviromentCreator insertAll() {

		daoType.insertOrUpdate(new EnviromentType(0,"POS"));
		daoType.insertOrUpdate(new EnviromentType(1,"ATM"));
		
		daoEnv.insertOrUpdate(new Enviroment(0, 0,"POS",       0,"C:/authentic/pos34/","172.29.4.157"));
		daoEnv.insertOrUpdate(new Enviroment(1, 1,"ATM",       1,"C:/authentic/atm34/",0,"172.29.40.10"));


		return this;
	}
	public EnviromentCreator showAll() {
		List<EnviromentType> allTypes = daoType.findAll();
		for (EnviromentType db : allTypes) {
			System.out.println(db.getName()+","+db.getId());
		}
		List<Enviroment> all = daoEnv.findAll();
		for (Enviroment db : all) {
			System.out.println(db.getName()+","+db.getType()+","+db.getPath()+","+db.getIdDataBase());
		}
		return this;
	}
}
