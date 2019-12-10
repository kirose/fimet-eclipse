package com.fimet.core.entity.sqlite.pojo;

public class Field implements Cloneable {
	private String key;
	private String value;
	public Field() {
		super();
	}
	public Field(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}