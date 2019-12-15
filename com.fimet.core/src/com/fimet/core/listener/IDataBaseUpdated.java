package com.fimet.core.listener;

import com.fimet.core.entity.sqlite.DataBase;

public interface IDataBaseUpdated extends IDataBaseListener {
	public void onDataBaseUpdated(DataBase e);
}
