package com.fimet.core.listener;

import com.fimet.core.entity.sqlite.DataBase;

public interface IDataBaseDisconnected extends IDataBaseListener {
	public void onDataBaseDisconnected(DataBase e);
}