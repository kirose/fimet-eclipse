package com.atenea.evaluator.operator.impl;

import com.atenea.commons.exception.ParserFormulaException;
import com.atenea.evaluator.operator.OperatorBinary;
import com.atenea.evaluator.type.Expression;
import com.atenea.evaluator.type.TypeDouble;
import com.atenea.evaluator.type.TypeInteger;
import com.atenea.evaluator.type.TypeNumber;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class Sub extends OperatorBinary {
	public String getName() {
		return "-";
	}
	public int getPrecedence() {
		return PRECEDENCE4;
	}
	@Override
	public TypeNumber eval(Expression left, Expression right) {
		left = left.eval();
		right = right.eval();
		if (left instanceof TypeDouble) {
			if (right instanceof TypeDouble) {
				return new TypeDouble(((TypeDouble)left.eval()).getValue() - ((TypeDouble)right.eval()).getValue());
			} else if (right instanceof TypeInteger) {
				return new TypeDouble(((TypeDouble)left.eval()).getValue() - ((TypeInteger)right.eval()).getValue());
			}
		} else if (left instanceof TypeInteger) {
			if (right instanceof TypeDouble) {
				return new TypeDouble(((TypeInteger)left.eval()).getValue() - ((TypeDouble)right.eval()).getValue());
			} else if (right instanceof TypeInteger) {
				return new TypeInteger(((TypeInteger)left.eval()).getValue() - ((TypeInteger)right.eval()).getValue());
			}			
		}
		throw new ParserFormulaException("No existe el operador <"+left.getClass()+"> " + this.getName() + " <" + right.getClass() + ">");
	}
	public Class<?> getType(){
		return TypeNumber.class;
	}
}
