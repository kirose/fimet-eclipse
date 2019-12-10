package com.fimet.core.entity.sqlite.pojo;


public class SimulatorField implements Cloneable {
	public static final char FIXED = 'F';
	public static final char CUSTOM = 'C';
	private String idField;
	private char type;
	private String value;
	public SimulatorField() {
		super();
	}
	public SimulatorField(String idField, char type, String value) {
		super();
		this.idField = idField;
		this.type = type;
		this.value = value;
	}
	public String getIdField() {
		return idField;
	}
	public void setIdField(String idField) {
		this.idField = idField;
	}
	public char getType() {
		return type;
	}
	public void setType(char type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public SimulatorField clone() throws CloneNotSupportedException {
		return (SimulatorField) super.clone();
	}
}