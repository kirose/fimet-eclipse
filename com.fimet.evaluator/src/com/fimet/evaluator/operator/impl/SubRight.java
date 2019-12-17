package com.fimet.evaluator.operator.impl;

import com.fimet.evaluator.operator.OperatorRight;
import com.fimet.evaluator.operator.OperatorUnary;
import com.fimet.evaluator.type.TypeDouble;
import com.fimet.evaluator.type.TypeInteger;
import com.fimet.evaluator.type.TypeNumber;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class SubRight extends OperatorUnary implements OperatorRight {
	public String getName() {
		return "-";
	}
	public int getPrecedence() {
		return PRECEDENCE5;
	}
	public TypeDouble eval(TypeDouble v0){
		return new TypeDouble(-v0.getValue());
	}
	public TypeInteger eval(TypeInteger v0){
		return new TypeInteger(-v0.getValue());
	}
	public Class<?> getType(){
		return TypeNumber.class;
	}
}
