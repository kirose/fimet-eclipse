package com.atenea.evaluator.operator.impl;

import com.atenea.commons.exception.ParserFormulaException;
import com.atenea.evaluator.operator.OperatorBinary;
import com.atenea.evaluator.type.Expression;
import com.atenea.evaluator.type.TypeBoolean;
import com.atenea.evaluator.type.TypeDouble;
import com.atenea.evaluator.type.TypeInteger;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class Ge extends OperatorBinary {
	public String getName() {
		return ">=";
	}
	public int getPrecedence() {
		return PRECEDENCE3;
	}
	@Override
	public TypeBoolean eval(Expression left, Expression right) {
		left = left.eval();
		right = right.eval();
		if (left instanceof TypeDouble) {
			if (right instanceof TypeDouble) {
				return TypeBoolean.valueOf(((TypeDouble)left.eval()).getValue() >= ((TypeDouble)right.eval()).getValue());
			} else if (right instanceof TypeInteger) {
				return TypeBoolean.valueOf(((TypeDouble)left.eval()).getValue() >= ((TypeInteger)right.eval()).getValue());
			}
		} else if (left instanceof TypeInteger) {
			if (right instanceof TypeDouble) {
				return TypeBoolean.valueOf(((TypeInteger)left.eval()).getValue() >= ((TypeDouble)right.eval()).getValue());
			} else if (right instanceof TypeInteger) {
				return TypeBoolean.valueOf(((TypeInteger)left.eval()).getValue() >= ((TypeInteger)right.eval()).getValue());
			}			
		}
		throw new ParserFormulaException("No existe el operador <"+left.getClass()+"> " + this.getName() + " <" + right.getClass() + ">");
	}
	public Class<?> getType(){
		return TypeBoolean.class;
	}
}
