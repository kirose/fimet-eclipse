package com.fimet.core.entity.sqlite;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
@DatabaseTable(tableName = "Parser")
public class Parser implements IRuleValue {
	@DatabaseField(id = true)
	private Integer id;
	@DatabaseField(canBeNull = false)
	private String name;
	@DatabaseField(canBeNull = false)
	private String parserClass;
	@DatabaseField(canBeNull = false)
	private Integer idConverter;
	@DatabaseField(canBeNull = false)
	private Integer idGroup;
	@DatabaseField(canBeNull = false)
	private Integer type;
	@DatabaseField(canBeNull = false)
	private String keySequence;
	public Parser() {}
	public Parser(Integer idParser, String name, Integer idFieldFormatGroup, String parserClass, Integer idConverter, Integer type, String keySequence) {
		super();
		this.idGroup = idFieldFormatGroup;
		this.id = idParser;
		this.name = name;
		this.parserClass = parserClass;
		this.idConverter = idConverter;
		this.type = type;
		this.keySequence = keySequence;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer idParser) {
		this.id = idParser;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getIdGroup() {
		return idGroup;
	}
	public void setIdGroup(Integer idGroup) {
		this.idGroup = idGroup;
	}
	public Integer getIdConverter() {
		return idConverter;
	}
	public void setIdConverter(Integer idConverter) {
		this.idConverter = idConverter;
	}
	public String getParserClass() {
		return parserClass;
	}
	public void setParserClass(String parserClass) {
		this.parserClass = parserClass;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return name;
	}
	public String getKeySequence() {
		return keySequence;
	}
	public void setKeySequence(String keySequence) {
		this.keySequence = keySequence;
	}
}
