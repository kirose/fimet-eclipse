package com.fimet.core.entity.sqlite.type;


import com.fimet.core.IParserManager;
import com.fimet.core.Manager;
import com.fimet.core.ISO8583.parser.IParser;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.IntType;

public class ParserType extends IntType {

	 private static ParserType instance;
	 public static ParserType getSingleton() {
		if (instance == null) {
			instance = new ParserType();
		}
		return instance;
	 }
	 protected ParserType() {
		super(SqlType.INTEGER, new Class<?>[] {IParser.class});
	 }
	 @Override
	 public Object javaToSqlArg(FieldType type, Object obj) {
		return obj != null ? ((IParser) obj).getId() : null;
	 }
	 @Override
	 public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
		return sqlArg != null ? Manager.get(IParserManager.class).getParser((Integer)sqlArg) : null;
	 }
}
