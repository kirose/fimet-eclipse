package com.atenea.evaluator.operator.impl;

import com.atenea.evaluator.operator.OperatorRight;
import com.atenea.evaluator.operator.OperatorUnary;
import com.atenea.evaluator.type.TypeBoolean;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class Not extends OperatorUnary implements OperatorRight {
	public String getName() {
		return "!";
	}
	public int getPrecedence() {
		return PRECEDENCE6;
	}
	public TypeBoolean eval(TypeBoolean v0){
		return TypeBoolean.valueOf(!v0.getValue());
	}
	public Class<?> getType(){
		return TypeBoolean.class;
	}
}
