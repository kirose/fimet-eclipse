package com.fimet.core.impl.swt.database;

import java.io.File;
import java.util.List;

import com.fimet.core.entity.sqlite.DataBase;

public interface IDataBaseImporter {
	List<DataBase> parse(File file, String key);
}
