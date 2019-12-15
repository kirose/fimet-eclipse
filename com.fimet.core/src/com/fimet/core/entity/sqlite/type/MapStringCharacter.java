package com.fimet.core.entity.sqlite.type;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.StringType;

public class MapStringCharacter extends StringType {

	private static MapStringCharacter instance;
	private Gson gson;
	public static MapStringCharacter getSingleton() {
		if (instance == null) {
			instance = new MapStringCharacter();
		}
		return instance;
	}
	protected MapStringCharacter() {
		super(SqlType.STRING, new Class<?>[] {Map.class});
		gson = new Gson();
	}
	 @Override
	 @SuppressWarnings("unchecked")
	 public Object javaToSqlArg(FieldType type, Object obj) {
		 return obj != null ? mapToJson((Map<String, Character>) obj) : null;
	 }
	 @Override
	 public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
		 return sqlArg != null ? jsonToMap((String) sqlArg) : null;
	 }
	 private String mapToJson(Map<String, Character> obj) {
		 return getSingleton().gson.toJson(obj);
	 }
	 private Map<String, Character> jsonToMap(String json) {
		 Type type = new TypeToken<Map<String, Character>>(){}.getType();
		 return getSingleton().gson.fromJson(json, type);
	 }
}
