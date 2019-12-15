package com.fimet.core.entity.sqlite.type;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.StringType;

public class ListStringType extends StringType {

	private static ListStringType instance;
	private Gson gson;
	public static ListStringType getSingleton() {
		if (instance == null) {
			instance = new ListStringType();
		}
		return instance;
	}
	protected ListStringType() {
		super(SqlType.STRING, new Class<?>[] {String.class});
		gson = new Gson();
	}
	 @Override
	 @SuppressWarnings("unchecked")
	 public Object javaToSqlArg(FieldType type, Object obj) {
		 return obj != null ? taskIssuerToJson((List<String>) obj) : null;
	 }
	 @Override
	 public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
		 return sqlArg != null ? jsonToField((String) sqlArg) : null;
	 }
	 private String taskIssuerToJson(List<String> obj) {
		 return getSingleton().gson.toJson(obj);
	 }
	 private List<String> jsonToField(String json) {
		 Type type = new TypeToken<List<String>>(){}.getType();
		 return getSingleton().gson.fromJson(json, type);
	 }
}
