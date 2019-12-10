package com.fimet.core.entity.sqlite.type;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.fimet.core.entity.sqlite.pojo.Field;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.StringType;

public class ListFieldType extends StringType {

	 private static ListFieldType instance;
	 private Gson gson;
	 public static ListFieldType getSingleton() {
		if (instance == null) {
			instance = new ListFieldType();
		}
		return instance;
	 }
	 protected ListFieldType() {
		super(SqlType.STRING, new Class<?>[] {Field.class});
		gson = new Gson();
	 }
	 @Override
	 @SuppressWarnings("unchecked")
	 public Object javaToSqlArg(FieldType type, Object obj) {
		List<Field> myFieldClass = (List<Field>) obj;
		return myFieldClass != null ? taskIssuerToJson(myFieldClass) : null;
	 }
	 @Override
	 public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
		return sqlArg != null ? jsonToField((String) sqlArg) : null;
	 }
	 private String taskIssuerToJson(List<Field> obj) {
		return getSingleton().gson.toJson(obj);
	 }
	 private List<Field> jsonToField(String json) {
		Type type = new TypeToken<List<Field>>(){}.getType();
		return getSingleton().gson.fromJson(json, type);
	 }
}
