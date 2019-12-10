package com.atenea.evaluator.operator;

import com.atenea.evaluator.type.Expression;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public interface OperatorRight extends Expression{
	public Expression getRight();
	public Expression setRight(Expression e);
	public int getPrecedence();
	public String getName();
}

