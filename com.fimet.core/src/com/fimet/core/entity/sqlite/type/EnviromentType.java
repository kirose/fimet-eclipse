package com.fimet.core.entity.sqlite.type;


import com.fimet.core.IEnviromentManager;
import com.fimet.core.Manager;
import com.fimet.core.entity.sqlite.Enviroment;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.IntType;

public class EnviromentType extends IntType {

	 private static EnviromentType instance;
	 public static EnviromentType getSingleton() {
		if (instance == null) {
			instance = new EnviromentType();
		}
		return instance;
	 }
	 protected EnviromentType() {
		super(SqlType.INTEGER, new Class<?>[] {Enviroment.class});
	 }
	 @Override
	 public Object javaToSqlArg(FieldType type, Object obj) {
		return obj != null ? ((Enviroment) obj).getId() : null;
	 }
	 @Override
	 public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
		return sqlArg != null ? Manager.get(IEnviromentManager.class).getEnviroment((Integer)sqlArg) : null;
	 }
}
