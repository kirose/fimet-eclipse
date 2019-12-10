package com.fimet.cfg.db;


import java.sql.SQLException;

import com.fimet.cfg.db.eg.fieldformat.Nacional;
import com.fimet.cfg.enums.Parser;
import com.fimet.core.entity.sqlite.FieldFormatGroup;
import com.fimet.persistence.sqlite.dao.FieldFormatDAO;
import com.fimet.persistence.sqlite.dao.FieldFormatGroupDAO;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import static com.fimet.cfg.enums.FieldFormatGroup.*;

public class FieldFormatCreator implements ICreator {
	FieldFormatGroupDAO daoGroup;
	FieldFormatDAO dao;
	ConnectionSource connection;
	public FieldFormatCreator(ConnectionSource connection) {
		super();
		this.connection = connection;
		daoGroup = new FieldFormatGroupDAO(connection);
		dao = new FieldFormatDAO(connection);
	}
	public FieldFormatCreator create() throws SQLException {
		TableUtils.createTableIfNotExists(connection, FieldFormatGroup.class);
		TableUtils.createTableIfNotExists(connection, com.fimet.core.entity.sqlite.FieldFormat.class);
		return this;
	}
	public FieldFormatCreator drop() throws SQLException {
		TableUtils.dropTable(connection, com.fimet.core.entity.sqlite.FieldFormat.class, true);
		TableUtils.dropTable(connection, FieldFormatGroup.class, true);
		return this;
	}
	public FieldFormatCreator delete(Parser parser) throws SQLException {
		if (parser != null) {
			FieldFormatDAO dao = FieldFormatDAO.getInstance();
			int i = dao.deleteByParser(parser.getId());
			System.out.println("Deleted "+i+" rows");
		}
		return this;
	}
	public FieldFormatCreator insertAll() {
		
		insertGroups();
		
		insertNacional();

		
		Long count = dao.count();
		if (count > 0) {
			return this;
		}
		return this;
	}
	private void insertGroups() {
		daoGroup.insertOrUpdate(new FieldFormatGroup(NACIONAL.getId(),NACIONAL.getName()));
		
	}
	public FieldFormatCreator insertNacional() {
		new Nacional(dao).insert();
		return this;
	}
}
