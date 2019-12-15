package com.fimet.core.listener;

import com.fimet.core.entity.sqlite.DataBase;

public interface IDataBaseConnected extends IDataBaseListener {
	public void onDataBaseConnected(DataBase e);
}
