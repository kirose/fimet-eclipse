package com.atenea.evaluator.operator;

import com.atenea.evaluator.type.Expression;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public interface OperatorLeft extends Expression{
	public Expression getLeft();
	public Expression setLeft(Expression e);
	public OperatorLeft replaceLeftGroup(OperatorRight right);
	public int getPrecedence();
	public String getName();
}
