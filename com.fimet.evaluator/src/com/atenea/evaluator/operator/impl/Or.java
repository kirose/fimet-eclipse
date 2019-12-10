package com.atenea.evaluator.operator.impl;

import com.atenea.commons.exception.ParserFormulaException;
import com.atenea.evaluator.operator.OperatorBinary;
import com.atenea.evaluator.type.Expression;
import com.atenea.evaluator.type.TypeBoolean;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class Or extends OperatorBinary {
	public String getName() {
		return "||";
	}
	public int getPrecedence() {
		return PRECEDENCE1;
	}
	public TypeBoolean eval(Expression v0, Expression v1){
		if (v0.getType() == TypeBoolean.class && v1.getType() == TypeBoolean.class) {
			if (((TypeBoolean)v0.eval()).getValue()) {
				return TypeBoolean.TRUE;
			} else if(((TypeBoolean)v1.eval()).getValue()) {
				return TypeBoolean.TRUE;
			} else {
				return TypeBoolean.FALSE;
			}
		}
		throw new ParserFormulaException("No existe el operador <"+left.getClass()+"> " + this.getName() + " <" + right.getClass() + ">");
	}
	public Class<?> getType(){
		return TypeBoolean.class;
	}
}
