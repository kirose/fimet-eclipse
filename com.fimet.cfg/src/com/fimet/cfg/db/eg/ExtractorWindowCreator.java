package com.fimet.cfg.db.eg;


import static com.fimet.cfg.enums.EnviromentType.*;

import java.sql.SQLException;

import com.fimet.core.entity.sqlite.ExtractorWindow;
import com.fimet.core.entity.sqlite.pojo.Time;
import com.fimet.persistence.sqlite.dao.ExtractorWindowDAO;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class ExtractorWindowCreator implements ICreator {
	ExtractorWindowDAO dao = ExtractorWindowDAO.getInstance();
	ConnectionSource connection;
	public ExtractorWindowCreator(ConnectionSource connection) {
		super();
		this.connection = connection;
		dao = new ExtractorWindowDAO(connection);
	}
	public ExtractorWindowCreator create() throws SQLException {
		TableUtils.createTableIfNotExists(connection, ExtractorWindow.class);
		return this;
	}
	public ExtractorWindowCreator drop() throws SQLException {
		TableUtils.dropTable(connection, ExtractorWindow.class, true);
		return this;
	}
	public ExtractorWindowCreator insertAll() {

		dao.insertOrUpdate(new ExtractorWindow(POS34.getId(),"pos",1,new Time(03,05,00),new Time(04,05,00)));
		dao.insertOrUpdate(new ExtractorWindow(POS34.getId(),"pos",2,new Time(04,05,00),new Time(11,05,00)));
		dao.insertOrUpdate(new ExtractorWindow(POS34.getId(),"pos",3,new Time(11,05,00),new Time(14,05,00)));
		dao.insertOrUpdate(new ExtractorWindow(POS34.getId(),"pos",4,new Time(14,05,00),new Time(17,05,00)));
		dao.insertOrUpdate(new ExtractorWindow(POS34.getId(),"pos",5,new Time(17,05,00),new Time(20,05,00)));
		dao.insertOrUpdate(new ExtractorWindow(POS34.getId(),"pos",6,new Time(20,05,00),new Time(24,05,00)));
		dao.insertOrUpdate(new ExtractorWindow(POS34.getId(),"pos",7,new Time(24,05,00),new Time(24+1,05,00)));
		dao.insertOrUpdate(new ExtractorWindow(POS34.getId(),"pos",8,new Time(24+1,05,00),new Time(24+3,05,00)));
		
//		dao.insertOrUpdate(new ExtractorWindow(POS45.getId(),"pos",1,new Time(03,05,00),new Time(04,05,00)));
//		dao.insertOrUpdate(new ExtractorWindow(POS45.getId(),"pos",2,new Time(04,05,00),new Time(11,05,00)));
//		dao.insertOrUpdate(new ExtractorWindow(POS45.getId(),"pos",3,new Time(11,05,00),new Time(14,05,00)));
//		dao.insertOrUpdate(new ExtractorWindow(POS45.getId(),"pos",4,new Time(14,05,00),new Time(17,05,00)));
//		dao.insertOrUpdate(new ExtractorWindow(POS45.getId(),"pos",5,new Time(17,05,00),new Time(20,05,00)));
//		dao.insertOrUpdate(new ExtractorWindow(POS45.getId(),"pos",6,new Time(20,05,00),new Time(24,05,00)));
//		dao.insertOrUpdate(new ExtractorWindow(POS45.getId(),"pos",7,new Time(24,05,00),new Time(24+1,05,00)));
//		dao.insertOrUpdate(new ExtractorWindow(POS45.getId(),"pos",8,new Time(24+1,05,00),new Time(24+3,05,00)));
		
		
		dao.insertOrUpdate(new ExtractorWindow(ATM34.getId(),"atm",1,new Time(10-12,00,00),new Time(22,00,00)));
//		dao.insertOrUpdate(new ExtractorWindow(ATM45.getId(),"atm",1,new Time(10-12,00,00),new Time(22,00,00)));
		
		dao.insertOrUpdate(new ExtractorWindow(CEL34.getId(),"cel",1,new Time(10-12,00,00),new Time(22,00,00)));
//		dao.insertOrUpdate(new ExtractorWindow(CEL45.getId(),"cel",1,new Time(10-12,00,00),new Time(22,00,00)));
		
		dao.insertOrUpdate(new ExtractorWindow(COR34.getId(),"cor",1,new Time(10-12,00,00),new Time(22,00,00)));
//		dao.insertOrUpdate(new ExtractorWindow(COR45.getId(),"cor",1,new Time(10-12,00,00),new Time(22,00,00)));
		return this;
	}
}
