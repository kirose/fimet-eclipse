package com.fimet.core.listener;

import com.fimet.core.entity.sqlite.DataBase;

public interface IDataBaseDeleted extends IDataBaseListener {
	public void onDataBaseDeleted(DataBase e);
}
