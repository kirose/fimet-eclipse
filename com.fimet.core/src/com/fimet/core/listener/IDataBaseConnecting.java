package com.fimet.core.listener;

import com.fimet.core.entity.sqlite.DataBase;

public interface IDataBaseConnecting extends IDataBaseListener {
	public void onDataBaseConnecting(DataBase e);
}
