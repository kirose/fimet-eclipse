package com.fimet.core.entity.sqlite.type;


import com.google.gson.Gson;
import com.fimet.core.entity.sqlite.pojo.Time;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.StringType;

public class TimeType extends StringType {

	 private static TimeType instance;
	 private Gson gson;
	 public static TimeType getSingleton() {
		if (instance == null) {
			instance = new TimeType();
		}
		return instance;
	 }
	 protected TimeType() {
		super(SqlType.STRING, new Class<?>[] {Time.class});
		gson = new Gson();
	 }
	 @Override
	 public Object javaToSqlArg(FieldType type, Object obj) {
		return obj != null ? getSingleton().gson.toJson((Time) obj) : null;
	 }
	 @Override
	 public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
		return sqlArg != null ? getSingleton().gson.fromJson((String)sqlArg, Time.class) : null;
	 }
}
