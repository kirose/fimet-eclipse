package com.fimet.evaluator.operator;

import com.fimet.commons.exception.ParserFormulaException;
import com.fimet.evaluator.type.Expression;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public abstract class OperatorUnary extends Operator{
	Expression operand;
	
	public Expression getLeft(){
		return operand;
	}
	public Expression setLeft(Expression left){
		operand = left;
		return this;
	}
	public Expression getRight(){
		return operand;
	}
	public Expression setRight(Expression right){
		operand = right;
		return this;
	}
	public Expression eval() {
		Expression o = operand.eval();
		try{
			return (Expression) this.getClass().getMethod("eval", o.getClass()).invoke(this, o);
		} catch(Exception e){
			throw new ParserFormulaException("No existe el operador "+ (this instanceof OperatorLeft ? "<"+o.getClass()+"> " + this.getName() :this.getName() + " <" + o.getClass() + ">"));
		}
	}
	public Class<?> getType() {
		return null;
	}
	public String toString(){
		return this instanceof OperatorLeft ?  "(" + operand + ")" + getName() : getName() + "(" + operand + ")";
	}
}
