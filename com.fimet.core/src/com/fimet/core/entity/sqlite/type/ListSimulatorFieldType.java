package com.fimet.core.entity.sqlite.type;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.fimet.core.entity.sqlite.pojo.SimulatorField;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.StringType;

public class ListSimulatorFieldType extends StringType {

	private static ListSimulatorFieldType instance;
	private Gson gson;
	public static ListSimulatorFieldType getSingleton() {
		if (instance == null) {
			instance = new ListSimulatorFieldType();
		}
		return instance;
	}
	protected ListSimulatorFieldType() {
		super(SqlType.STRING, new Class<?>[] {SimulatorField.class});
		gson = new Gson();
	}
	 @Override
	 @SuppressWarnings("unchecked")
	 public Object javaToSqlArg(FieldType type, Object obj) {
		 return obj != null ? taskIssuerToJson((List<SimulatorField>) obj) : null;
	 }
	 @Override
	 public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
		 return sqlArg != null ? jsonToField((String) sqlArg) : null;
	 }
	 private String taskIssuerToJson(List<SimulatorField> obj) {
		 return getSingleton().gson.toJson(obj);
	 }
	 private List<SimulatorField> jsonToField(String json) {
		 Type type = new TypeToken<List<SimulatorField>>(){}.getType();
		 return getSingleton().gson.fromJson(json, type);
	 }
}
