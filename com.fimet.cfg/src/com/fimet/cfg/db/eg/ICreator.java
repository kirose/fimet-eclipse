package com.fimet.cfg.db.eg;

import java.sql.SQLException;

public interface ICreator {
	ICreator drop() throws SQLException;
	ICreator create() throws SQLException;
	ICreator insertAll();
}
