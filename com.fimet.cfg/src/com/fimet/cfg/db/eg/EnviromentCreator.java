package com.fimet.cfg.db.eg;


import static com.fimet.cfg.enums.EnviromentType.*;

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

		daoType.insertOrUpdate(new EnviromentType(POS34.getId(),POS34.getName()));
		daoType.insertOrUpdate(new EnviromentType(ATM34.getId(),ATM34.getName()));
		daoType.insertOrUpdate(new EnviromentType(CEL34.getId(),CEL34.getName()));
		daoType.insertOrUpdate(new EnviromentType(COR34.getId(),COR34.getName()));
		
		
		daoEnv.insertOrUpdate(new Enviroment(0, POS34.getId(),"POS 3.4 LOCAL",       0,"C:/authentic/pos34/","172.29.4.157"));
		daoEnv.insertOrUpdate(new Enviroment(1, POS34.getId(),"POS 3.4 CANDY",       1,"/posprodu/standin/runtime/",0,"172.29.40.10"));
		daoEnv.insertOrUpdate(new Enviroment(2, POS34.getId(),"POS 3.4 BBVA",        2,"C:/authentic/pos34/","172.29.4.157"));
		daoEnv.insertOrUpdate(new Enviroment(3, POS34.getId(),"POS 3.4 BANAMEX",     3,"C:/authentic/pos34/","172.29.4.157"));
		daoEnv.insertOrUpdate(new Enviroment(4, POS34.getId(),"POS 3.4 OTROS",       4,"C:/authentic/pos34/","172.29.4.157"));
		daoEnv.insertOrUpdate(new Enviroment(5, POS34.getId(),"POS 3.4 INTERNOS",    5,"C:/authentic/pos34/","172.29.4.157"));
		daoEnv.insertOrUpdate(new Enviroment(6, ATM34.getId(),"ATM 3.4 CANDY",       6,"/posprodu/atm_mode/runtime/",0,"172.29.40.10"));
		daoEnv.insertOrUpdate(new Enviroment(7, ATM34.getId(),"ATM 3.4 BBVA",        7,"C:/authentic/atm34/","172.29.4.157"));
		daoEnv.insertOrUpdate(new Enviroment(8, ATM34.getId(),"ATM 3.4 BANAMEX",     8,"C:/authentic/atm34/","172.29.4.157"));
		daoEnv.insertOrUpdate(new Enviroment(9, ATM34.getId(),"ATM 3.4 EVO",         9,"C:/authentic/atm34/","172.29.4.157"));
		daoEnv.insertOrUpdate(new Enviroment(10,CEL34.getId(),"CEL 3.4 BBVA",        7,"C:/authentic/atm34/","172.29.4.157"));
		daoEnv.insertOrUpdate(new Enviroment(11,COR34.getId(),"COR 3.4 BBVA",        7,"C:/authentic/atm34/","172.29.4.157"));
		daoEnv.insertOrUpdate(new Enviroment(12,POS34.getId(),"POS 3.4 INCIDENTES",  7,"C:/authentic/pos34/","172.29.4.157"));
		daoEnv.insertOrUpdate(new Enviroment(13,ATM34.getId(),"ATM 3.4 INCIDENTES",  7,"C:/authentic/atm34/","172.29.4.157"));

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
