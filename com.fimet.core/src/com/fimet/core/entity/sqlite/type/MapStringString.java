package com.fimet.core.entity.sqlite.type;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.StringType;

public class MapStringString extends StringType {

	private static MapStringString instance;
	private Gson gson;
	public static MapStringString getSingleton() {
		if (instance == null) {
			instance = new MapStringString();
		}
		return instance;
	}
	protected MapStringString() {
		super(SqlType.STRING, new Class<?>[] {Map.class});
		gson = new Gson();
	}
	 @Override
	 @SuppressWarnings("unchecked")
	 public Object javaToSqlArg(FieldType type, Object obj) {
		 return obj != null ? mapToJson((Map<String, String>) obj) : null;
	 }
	 @Override
	 public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
		 return sqlArg != null ? jsonToMap((String) sqlArg) : null;
	 }
	 private String mapToJson(Map<String, String> obj) {
		 return getSingleton().gson.toJson(obj);
	 }
	 private Map<String, Character> jsonToMap(String json) {
		 Type type = new TypeToken<Map<String, String>>(){}.getType();
		 return getSingleton().gson.fromJson(json, type);
	 }
}
