package com.fimet.core.entity.sqlite.type;

import com.google.gson.Gson;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.StringType;

public class ArrayStringType extends StringType {
	 private static ArrayStringType instance;
	 private Gson gson;
	 public static ArrayStringType getSingleton() {
		if (instance == null) {
			instance = new ArrayStringType();
		}
		return instance;
	 }
	 protected ArrayStringType() {
		super(SqlType.STRING, new Class<?>[] {String.class});
		gson = new Gson();
	 }
	 @Override
	 public Object javaToSqlArg(FieldType type, Object obj) {
		String[] strings = (String[]) obj;
		return strings != null ? gson.toJson(strings) : null;
	 }
	 @Override
	 public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
		return sqlArg != null ? gson.fromJson((String)sqlArg, String[].class) : null;
	 }
}
