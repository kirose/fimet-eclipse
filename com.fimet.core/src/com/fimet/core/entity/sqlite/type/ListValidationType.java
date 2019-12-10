package com.fimet.core.entity.sqlite.type;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.fimet.core.entity.sqlite.pojo.Validation;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.StringType;

public class ListValidationType extends StringType {

	private static ListValidationType instance;
	private Gson gson;
	public static ListValidationType getSingleton() {
		if (instance == null) {
			instance = new ListValidationType();
		}
		return instance;
	}
	protected ListValidationType() {
		super(SqlType.STRING, new Class<?>[] {Validation.class});
		gson = new Gson();
	}
	 @Override
	 @SuppressWarnings("unchecked")
	 public Object javaToSqlArg(FieldType type, Object obj) {
		 List<Validation> myFieldClass = (List<Validation>) obj;
		 return myFieldClass != null ? taskIssuerToJson(myFieldClass) : null;
	 }
	 @Override
	 public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
		 return sqlArg != null ? jsonToTaskValidation((String) sqlArg) : null;
	 }
	 private String taskIssuerToJson(List<Validation> obj) {
		 return getSingleton().gson.toJson(obj);
	 }
	 private List<Validation> jsonToTaskValidation(String json) {
		 Type type = new TypeToken<List<Validation>>(){}.getType();
		 return getSingleton().gson.fromJson(json, type);
	 }
}
