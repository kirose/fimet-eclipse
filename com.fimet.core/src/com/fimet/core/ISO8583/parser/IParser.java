package com.fimet.core.ISO8583.parser;

import com.fimet.commons.converter.IConverter;
import com.fimet.core.IFieldParserManager;

public interface IParser {

	public static final int ISO8583 = 0;
	public static final int LAYOUT = 1;

	Integer getId();
	Integer getIdGroup();
	String getName();
	IConverter getConverter();
	boolean getValidateTypes();
	IFieldParserManager getFieldParserManager();
	String getKeySequence();
	byte[] formatMessage(IMessage msg);
	IMessage parseMessage(byte[] bytes);
}
