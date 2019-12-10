package com.atenea.evaluator.type;

/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class Variable extends Type {
	private Class<?> type;
	private String name;
	private Object value;
	public Variable(Class<?> type, String name, Object value) {
		super();
		this.type = type;
		this.name = name;
		this.value = value;
	}
	public Variable(Class<?> type, String name) {
		super();
		this.type = type;
		this.name = name;
	}
	public void setType(Class<?> type) {
		this.type = type;
	}
	@Override
	public Class<?> getType() {
		return type;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	@Override
	public Object evaluate() {
		return value;
	}
	public String getName() {
		return name;
	}
	public Object getValue() {
		return value;
	}
}
