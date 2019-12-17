package com.fimet.evaluator.operator.impl;

import com.fimet.commons.exception.ParserFormulaException;
import com.fimet.evaluator.operator.OperatorBinary;
import com.fimet.evaluator.type.Expression;
import com.fimet.evaluator.type.TypeBoolean;
import com.fimet.evaluator.type.TypeDouble;
import com.fimet.evaluator.type.TypeInteger;
import com.fimet.evaluator.type.TypeString;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class Ne extends OperatorBinary {
	public String getName() {
		return "!=";
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
				return TypeBoolean.valueOf(((TypeDouble)left.eval()).getValue() != ((TypeDouble)right.eval()).getValue());
			} else if (right instanceof TypeInteger) {
				return TypeBoolean.valueOf(((TypeDouble)left.eval()).getValue() != ((TypeInteger)right.eval()).getValue());
			}
		} else if (left instanceof TypeInteger) {
			if (right instanceof TypeDouble) {
				return TypeBoolean.valueOf(((TypeInteger)left.eval()).getValue() != ((TypeDouble)right.eval()).getValue());
			} else if (right instanceof TypeInteger) {
				return TypeBoolean.valueOf(((TypeInteger)left.eval()).getValue() != ((TypeInteger)right.eval()).getValue());
			}			
		} else if (left instanceof TypeBoolean && right instanceof TypeBoolean) {
			return TypeBoolean.valueOf(((TypeBoolean)left.eval()).getValue() != ((TypeBoolean)right.eval()).getValue()); 
		} else if (left instanceof TypeString && right instanceof TypeString) {
			return TypeBoolean.valueOf(!((TypeString)left.eval()).getValue().equals(((TypeString)right.eval()).getValue())); 
		}
		throw new ParserFormulaException("No existe el operador <"+left.getClass()+"> " + this.getName() + " <" + right.getClass() + ">");
	}
	public Class<?> getType(){
		return TypeBoolean.class;
	}
}
