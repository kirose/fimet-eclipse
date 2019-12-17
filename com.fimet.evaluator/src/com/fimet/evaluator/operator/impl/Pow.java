package com.fimet.evaluator.operator.impl;

import com.fimet.commons.exception.ParserFormulaException;
import com.fimet.evaluator.operator.OperatorBinary;
import com.fimet.evaluator.type.Expression;
import com.fimet.evaluator.type.TypeDouble;
import com.fimet.evaluator.type.TypeInteger;
import com.fimet.evaluator.type.TypeNumber;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class Pow extends OperatorBinary {
	public String getName() {
		return "^";
	}
	public int getPrecedence() {
		return PRECEDENCE5;
	}
	@Override
	public TypeNumber eval(Expression left, Expression right) {
		left = left.eval();
		right = right.eval();
		if (left instanceof TypeDouble) {
			if (right instanceof TypeDouble) {
				return new TypeDouble(Math.pow(((TypeDouble)left.eval()).getValue() , ((TypeDouble)right.eval()).getValue()));
			} else if (right instanceof TypeInteger) {
				return new TypeDouble(Math.pow(((TypeDouble)left.eval()).getValue() , ((TypeInteger)right.eval()).getValue()));
			}
		} else if (left instanceof TypeInteger) {
			if (right instanceof TypeDouble) {
				return new TypeDouble(Math.pow(((TypeInteger)left.eval()).getValue() , ((TypeDouble)right.eval()).getValue()));
			} else if (right instanceof TypeInteger) {
				return new TypeDouble(Math.pow(((TypeInteger)left.eval()).getValue() , ((TypeInteger)right.eval()).getValue()));
			}			
		}
		throw new ParserFormulaException("No existe el operador <"+left.getClass()+"> " + this.getName() + " <" + right.getClass() + ">");
	}
	public Class<?> getType(){
		return TypeNumber.class;
	}
}
