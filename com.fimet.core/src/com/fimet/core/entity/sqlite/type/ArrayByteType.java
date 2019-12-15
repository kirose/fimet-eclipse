package com.fimet.core.entity.sqlite.type;

import com.fimet.commons.converter.Converter;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.StringType;

public class ArrayByteType extends StringType {
	 private static ArrayByteType instance; 
	 public static ArrayByteType getSingleton() {
		if (instance == null) {
			instance = new ArrayByteType();
		}
		return instance;
	 }
	 protected ArrayByteType() {
		super(SqlType.STRING, new Class<?>[] {byte.class});
	 }
	 @Override
	 public Object javaToSqlArg(FieldType type, Object obj) {
		byte[] bytes = (byte[]) obj;
		return bytes != null ? new String(Converter.asciiToHex(bytes)) : null;
	 }
	 @Override
	 public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
		return sqlArg != null ? Converter.hexToAscii(((String)sqlArg).getBytes()) : null;
	 }
}
