package com.fimet.core.listener;

import com.fimet.core.entity.sqlite.DataBase;

public interface IDataBaseInserted extends IDataBaseListener {
	public void onDataBaseInserted(DataBase e);
}
