package com.fimet.evaluator.function.internal.impl;

import com.fimet.commons.exception.ParserFormulaException;
import com.fimet.core.parser.field.Field;
import com.fimet.evaluator.ParserFormula;
import com.fimet.evaluator.function.internal.Function;
import com.fimet.evaluator.token.Token;
import com.fimet.evaluator.type.Expression;
import com.fimet.evaluator.type.TypeString;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class FValue extends Function {
	public FValue(ParserFormula parserFormula){
		super(parserFormula);
	}
	public FValue(ParserFormula parserFormula, Expression ... args){
		super(parserFormula, args);
	}
	public Expression parse(Token token, ParserFormula parser, int end) {
		args = parser.parseArgs(end);
		if (args == null || args.length != 1){
			throw new ParserFormulaException("Sintaxis invalida se esperaban 1 argumento value (String arg1)");
		}
		return this;
	}
	public Expression eval(){
		Expression idField = args[0];
		if (!(idField instanceof TypeString)) {
			throw new ParserFormulaException("No existe la funcion eval (<"+idField.getClass()+">)");
		}
		return eval((TypeString)idField);
	}
	public TypeString eval(TypeString s){
		Field field = parserFormula.getMsg().getField(s.getValue());
		if (field == null) {
			return new TypeString(null);
		} else {
			return new TypeString(field.getValue());	
		}
		
	}
	public Class<?> getType(){
		return TypeString.class;
	}
	public String toString(){
		return "value('"+ ((TypeString)args[0]).getValue() + "')";
	}
}
