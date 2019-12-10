package com.fimet.core.ISO8583.parser;

import com.fimet.commons.converter.Converter;
import com.fimet.commons.converter.IConverter;
import com.fimet.core.IFieldParserManager;
import com.fimet.core.Manager;
import com.fimet.core.entity.sqlite.Parser;


public abstract class AbstractBaseParser implements IParser {
	
	/**
	 * The Field Parser Manager
	 */
	private static IFieldParserManager fieldParserManager = Manager.get(IFieldParserManager.class);
	/**
	 * Id Parser
	 */
	private int idParser;
	/**
	 * Parser Name
	 */
	private String name;
	/**
	 * Global Converter example 
	 * Bancomer: EBCDIC to ASCII
	 * Visa: ASCII to HEX
	 */
	private IConverter converter;
	private Integer idGroup;
	private boolean validateTypes = true;
	private String keySequence;
	public AbstractBaseParser(Parser entity){
		this.idParser = entity.getId();
		this.converter = Converter.get(entity.getIdConverter());
		this.name = entity.getName();
		this.idGroup = entity.getIdGroup();
		this.keySequence = entity.getKeySequence();
	}
	public IFieldParserManager getFieldParserManager() {
		return fieldParserManager;
	}
	public Integer getId() {
		return idParser;
	}
	public IConverter getConverter() {
		return converter;
	}
	public String getName() {
		return name;
	}
	@Override
	public String toString() {
		return name;
	}
	public boolean getValidateTypes() {
		return validateTypes;
	}
	public Integer getIdGroup() {
		return idGroup;
	}
	public String getKeySequence() {
		return keySequence;
	}
}
