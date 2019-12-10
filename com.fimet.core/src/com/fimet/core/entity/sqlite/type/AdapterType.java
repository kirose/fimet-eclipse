package com.fimet.core.entity.sqlite.type;


import com.fimet.core.Manager;
import com.fimet.core.ISO8583.adapter.IAdapter;
import com.fimet.core.ISO8583.adapter.IAdapterManager;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.IntType;

public class AdapterType extends IntType {

	 private static AdapterType instance;
	 public static AdapterType getSingleton() {
		if (instance == null) {
			instance = new AdapterType();
		}
		return instance;
	 }
	 protected AdapterType() {
		super(SqlType.INTEGER, new Class<?>[] {IAdapter.class});
	 }
	 @Override
	 public Object javaToSqlArg(FieldType type, Object obj) {
		return obj != null ? ((IAdapter) obj).getId() : null;
	 }
	 @Override
	 public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
		return sqlArg != null ? Manager.get(IAdapterManager.class).getAdapter((Integer)sqlArg) : null;
	 }
}
