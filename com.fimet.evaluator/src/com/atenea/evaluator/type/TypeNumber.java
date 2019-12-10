package com.atenea.evaluator.type;

import com.atenea.evaluator.type.Type;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public abstract class TypeNumber extends Type {
	abstract public Number evaluate();
	@Override
	public Class<?> getType() {
		return Number.class;
	}
}
