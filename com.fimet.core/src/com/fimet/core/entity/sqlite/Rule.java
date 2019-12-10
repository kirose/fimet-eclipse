package com.fimet.core.entity.sqlite;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
@DatabaseTable(tableName = "Rule")
public class Rule {
	
	public static final char EQUALS = '=';
	public static final char MATCHES = '~';
	public static final char CONTAINS = '>';
	
	@DatabaseField(id = true)
	private Integer id;
	@DatabaseField(canBeNull = false)
	private int idTypeEnviroment;
	@DatabaseField(canBeNull = false)
	private int idField;
	@DatabaseField(canBeNull = false)
	private String order;
	@DatabaseField(canBeNull = false)
	private String property;//IAP 'I', Interchange 'C', Interface 'F'
	@DatabaseField(canBeNull = false)
	private char operator;//equals '=', like '~', contains >
	@DatabaseField(canBeNull = false)
	private String pattern;// the pattern expected
	@DatabaseField(canBeNull = true)
	private Integer idResult;// the id simulator
	public Rule() {
	}
	public Rule(int idTypeEnviroment, int idField, String order, String property, char operator, String pattern) {
		super();
		this.idTypeEnviroment = idTypeEnviroment;
		this.idField = idField;
		this.order = order;
		this.property = property;
		this.operator = operator;
		this.pattern = pattern;
	}
	public Rule(int idTypeEnviroment, int idField, String order, String property, char operator, String pattern, Integer idResult) {
		super();
		this.idTypeEnviroment = idTypeEnviroment;
		this.idField = idField;
		this.order = order;
		this.property = property;
		this.operator = operator;
		this.pattern = pattern;
		this.idResult = idResult;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public void setType(String property) {
		this.property = property;
	}
	public char getOperator() {
		return operator;
	}
	public void setOperator(char operator) {
		this.operator = operator;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIdResult() {
		return idResult;
	}
	public void setIdResult(Integer idResult) {
		this.idResult = idResult;
	}
	public int getIdTypeEnviroment() {
		return idTypeEnviroment;
	}
	public void setIdTypeEnviroment(int idTypeEnviroment) {
		this.idTypeEnviroment = idTypeEnviroment;
	}
	public int getIdField() {
		return idField;
	}
	public void setIdField(int idField) {
		this.idField = idField;
	}
	public String getPattern() {
		return pattern;
	}
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	@Override
	public String toString() {
		return property +" "+ operator + " " + pattern + " " + idResult;
	}
	
}
