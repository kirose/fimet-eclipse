package com.fimet.cfg.db.eg;


import java.sql.SQLException;
import java.util.List;

import com.fimet.commons.Version;
import com.fimet.core.entity.sqlite.DataBase;
import com.fimet.persistence.sqlite.dao.DataBaseDAO;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DataBaseCreator implements ICreator {
	DataBaseDAO dao;
	ConnectionSource connection;
	public DataBaseCreator(ConnectionSource connection) {
		super();
		this.connection = connection;
		dao = new DataBaseDAO(connection);
	}
	public DataBaseCreator create() throws SQLException {
		TableUtils.createTableIfNotExists(connection, DataBase.class);
		return this;
	}
	public DataBaseCreator drop() throws SQLException {
		TableUtils.dropTable(connection, DataBase.class, true);
		return this;
	}
	public DataBaseCreator insertAll() {

		dao.insertOrUpdate(new DataBase(0,"LOCAL BCMR",      "",          "orcl","localhost",   "1521","bcmr_pos_owner",Version.getInstance().encrypt("Bcmrp0s0wn3r21"), true));
		dao.insertOrUpdate(new DataBase(1,"POS CANDY",       "RPOS_OWNER","DESA","172.29.40.11","1521","Uposdesa",      Version.getInstance().encrypt("up0sd3s418"),     true));
		dao.insertOrUpdate(new DataBase(2,"POS BBVA",        "",          "DESA","172.29.40.8", "1521","bcmr_pos_owner",Version.getInstance().encrypt("bcmrp0s0wn3r20"), true));
		dao.insertOrUpdate(new DataBase(3,"POS BANAMEX",     "",          "DESA","172.29.40.8", "1521","bnmx_pos_owner",Version.getInstance().encrypt("bnmxp0s0wn3r42"), true));
		dao.insertOrUpdate(new DataBase(4,"POS OTROS",       "",          "DESA","172.29.40.8", "1521","otr_pos_owner", Version.getInstance().encrypt("otRp0s0wn3r97"),  true));
		dao.insertOrUpdate(new DataBase(5,"POS INTERNOS",    "",          "DESA","172.29.40.8", "1521","int_pos_owner", Version.getInstance().encrypt("bcmrp0s0wn3r20"), true));
		dao.insertOrUpdate(new DataBase(6,"ATM CANDY",       "",          "AUTH","172.29.40.11","1521","Uatmdesa",      Version.getInstance().encrypt("ua7md3s418"),     true));
		dao.insertOrUpdate(new DataBase(7,"ATM BBVA",        "",          "AUTH","172.29.40.8", "1521","bcmr_atm_owner",Version.getInstance().encrypt("bcmr4tm0wn3r31"), true));
		dao.insertOrUpdate(new DataBase(8,"ATM BANAMEX",     "",          "AUTH","172.29.40.8", "1521","bnmx_atm_owner",Version.getInstance().encrypt("bnmx4tm0wn3r53"), true));
		dao.insertOrUpdate(new DataBase(9,"ATM EVO",         "",          "AUTH","172.29.40.8", "1521","evo_atm_owner", Version.getInstance().encrypt("ev04tm0wn3r78"),  true));
		dao.insertOrUpdate(new DataBase(10,"POS INCIDENTES", "",          "DESA","172.29.40.8", "1521","inc_pos_owner", Version.getInstance().encrypt("incp0s0wn3r18"),  true));
		dao.insertOrUpdate(new DataBase(11,"ATM INCIDENTES", "",          "AUTH","172.29.40.8", "1521","inc_atm_owner", Version.getInstance().encrypt("inc4tm0wn3r79"),  true));
		
		return this;
	}
	public DataBaseCreator showAll() {
		List<DataBase> all = dao.findAll();
		for (DataBase db : all) {
			System.out.println(db.getName()+","+db.getSid()+","+db.getSchema()+","+db.getAddress()+","+db.getPort()+","+db.getUser()+","+db.getPassword());
		}
		if (dao.count() > 0) {
			return this;
		}
		return this;
	}
}
