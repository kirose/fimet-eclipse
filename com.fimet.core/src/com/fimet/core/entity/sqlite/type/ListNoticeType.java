package com.fimet.core.entity.sqlite.type;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.fimet.core.entity.sqlite.pojo.Notice;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.StringType;

public class ListNoticeType extends StringType {

	private static ListNoticeType instance;
	private Gson gson;
	public static ListNoticeType getSingleton() {
		if (instance == null) {
			instance = new ListNoticeType();
		}
		return instance;
	}
	protected ListNoticeType() {
		super(SqlType.STRING, new Class<?>[] {Notice.class});
		gson = new Gson();
	}
	 @Override
	 @SuppressWarnings("unchecked")
	 public Object javaToSqlArg(FieldType type, Object obj) {
		 List<Notice> myFieldClass = (List<Notice>) obj;
		 return myFieldClass != null ? taskIssuerToJson(myFieldClass) : null;
	 }
	 @Override
	 public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
		 return sqlArg != null ? jsonToNotice((String) sqlArg) : null;
	 }
	 private String taskIssuerToJson(List<Notice> obj) {
		 return getSingleton().gson.toJson(obj);
	 }
	 private List<Notice> jsonToNotice(String json) {
		 Type type = new TypeToken<List<Notice>>(){}.getType();
		 return getSingleton().gson.fromJson(json, type);
	 }
}
