package com.fimet.evaluator.type;

/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public abstract class TypeString extends Type {
	abstract public String evaluate();
	@Override
	public Class<?> getType() {
		return String.class;
	}
}
