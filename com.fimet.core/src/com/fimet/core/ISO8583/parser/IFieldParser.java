package com.fimet.core.ISO8583.parser;

import com.fimet.commons.data.reader.IReader;
import com.fimet.commons.data.writer.IWriter;
import com.fimet.core.entity.sqlite.FieldFormatGroup;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public interface IFieldParser {
	
	String getName();
	String getIdField();
	String getIdOrder();
	
	String getType();
	int getLength();
	boolean isValidValue(String value);
	boolean isValidLength(String value);
	short[] getAddress();
	
	byte[] parse(IReader reader, IMessage message);
	byte[] format(IWriter writer, IMessage message);
	FieldFormatGroup getGroup();
	//FieldFormat getFieldFormat();
	
	boolean isFixed();
	boolean hasChild(String idChild);
	int indexOfChild(String idChild);
	String getIdChild(int index);
}
