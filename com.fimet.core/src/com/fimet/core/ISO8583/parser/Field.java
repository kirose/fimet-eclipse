package com.fimet.core.ISO8583.parser;

/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class Field {
	String idField;
	byte[] bytes;
	Field[] children;
	Field() {
	}
	Field(String idField, byte[] bytes) {
		this.idField = idField;
		this.bytes = bytes;
	}
	Field(String idField, byte[] bytes, Field[] children) {
		this.idField = idField;
		this.bytes = bytes;
		this.children = children;
	}
	void add(Field child) {
		if (children == null) {
			children = new Field[] {child};
		} else {
			Field[] resized = new Field[children.length+1];
			System.arraycopy(children, 0, resized, 0, children.length);
			resized[children.length] = child;
			children = resized; 
		}
	}
	public String getValue() {
		return bytes != null ? new String(bytes) : null;
	}
	public byte[] getBytes() {
		return bytes;
	}
	public String getIdField() {
		return idField;
	}
	public boolean hasChildren() {
		return children != null && children.length > 0;
	}
	public Field[] getChildren() {
		return children;
	}
	public String getKey() {
		return idField != null && idField.indexOf('.') != -1 ? idField.substring(idField.lastIndexOf('.')+1) : idField;
	}
}