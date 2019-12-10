package com.atenea.evaluator.operator.impl;

import com.atenea.commons.exception.ParserFormulaException;
import com.atenea.evaluator.operator.OperatorBinary;
import com.atenea.evaluator.type.Expression;
import com.atenea.evaluator.type.TypeBoolean;
import com.atenea.evaluator.type.TypeDouble;
import com.atenea.evaluator.type.TypeInteger;
import com.atenea.evaluator.type.TypeString;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class Eq extends OperatorBinary {
	public String getName() {
		return "==";
	}
	public int getPrecedence() {
		return PRECEDENCE2;
	}
	@Override
	public TypeBoolean eval(Expression left, Expression right) {
		left = left.eval();
		right = right.eval();
		if (left instanceof TypeDouble) {
			if (right instanceof TypeDouble) {
				return TypeBoolean.valueOf(((TypeDouble)left.eval()).getValue() == ((TypeDouble)right.eval()).getValue());
			} else if (right instanceof TypeInteger) {
				return TypeBoolean.valueOf(((TypeDouble)left.eval()).getValue() == ((TypeInteger)right.eval()).getValue());
			}
		} else if (left instanceof TypeInteger) {
			if (right instanceof TypeDouble) {
				return TypeBoolean.valueOf(((TypeInteger)left.eval()).getValue() == ((TypeDouble)right.eval()).getValue());
			} else if (right instanceof TypeInteger) {
				return TypeBoolean.valueOf(((TypeInteger)left.eval()).getValue() == ((TypeInteger)right.eval()).getValue());
			}			
		} else if (left instanceof TypeString && right instanceof TypeString) {
			String s0 = ((TypeString)left).getValue();
			String s1 = ((TypeString)right).getValue();
			if (s0 == null) {
				if (s1 == null) {
					return TypeBoolean.TRUE;
				} else {
					return TypeBoolean.FALSE;
				}
			}
			return TypeBoolean.valueOf(s0.equals(s1));
		} else if (left instanceof TypeBoolean && right instanceof TypeBoolean) {
			return TypeBoolean.valueOf(((TypeBoolean)left).getValue() == ((TypeBoolean)right).getValue());
		}
		throw new ParserFormulaException("No existe el operador <"+left.getClass()+"> " + this.getName() + " <" + right.getClass() + ">");
	}
	public Class<?> getType(){
		return TypeBoolean.class;
	}
}
