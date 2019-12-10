package com.fimet.core.entity.sqlite.type;


import com.fimet.core.ISimulatorManager;
import com.fimet.core.Manager;
import com.fimet.core.simulator.ISimulator;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.IntType;

public class SimulatorType extends IntType {

	 private static SimulatorType instance;
	 public static SimulatorType getSingleton() {
		if (instance == null) {
			instance = new SimulatorType();
		}
		return instance;
	 }
	 protected SimulatorType() {
		super(SqlType.INTEGER, new Class<?>[] {ISimulator.class});
	 }
	 @Override
	 public Object javaToSqlArg(FieldType type, Object obj) {
		return obj != null ? ((ISimulator) obj).getId() : null;
	 }
	 @Override
	 public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
		return sqlArg != null ? Manager.get(ISimulatorManager.class).getSimulator((Integer)sqlArg) : null;
	 }
}
