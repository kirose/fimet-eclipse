package com.atenea.evaluator.operator;

import com.atenea.commons.exception.ParserFormulaException;
import com.atenea.evaluator.type.Expression;
import com.atenea.evaluator.type.TypeInteger;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public abstract class OperatorBinary extends Operator implements OperatorLeft, OperatorRight {

	protected Expression left;
	protected Expression right;
	public Class<?> getType(){
		return TypeInteger.class;
	}
	public Expression getLeft() {
		return left;
	}
	public Expression setLeft(Expression e) {
		this.left = e;
		return this;
	}
	public OperatorLeft replaceLeftGroup(OperatorRight opRight) {
		if(left instanceof OperatorLeft && ((OperatorLeft) left).getPrecedence() >= opRight.getPrecedence()){
			((OperatorLeft)left).replaceLeftGroup(opRight);
		} else {
			opRight.setRight(left);
			left = opRight;
		}
		return this;
	}
	public Expression getRight() {
		return right;
	}
	public Expression setRight(Expression e) {
		this.right = e;
		return this;
	}
	public OperatorRight replaceRightGroup(OperatorLeft left) {
		throw new ParserFormulaException("Metodo no implementado aun");
	}
	public Expression eval() {
		return eval(left, right);
	}
	public abstract Expression eval(Expression left, Expression right);
	public String toString(){
		return "(" + left +getName() + right + ")";
	}
}
