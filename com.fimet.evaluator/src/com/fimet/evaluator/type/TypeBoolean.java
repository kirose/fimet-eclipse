package com.fimet.evaluator.type;

/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public abstract class TypeBoolean extends Type {
	public static TRUE TRUE = new TRUE();
	public static FALSE FALSE = new FALSE();
	public TypeBoolean() {}
	abstract public Boolean evaluate();
	@Override
	public Class<?> getType() {
		return Boolean.class;
	}
}
