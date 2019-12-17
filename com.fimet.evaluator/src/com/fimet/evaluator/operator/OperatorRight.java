package com.fimet.evaluator.operator;

import com.fimet.evaluator.type.Expression;
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

