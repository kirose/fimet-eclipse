package com.fimet.evaluator.function.internal.impl;


import com.fimet.commons.exception.ParserFormulaException;
import com.fimet.core.parser.field.Field;
import com.fimet.evaluator.ParserFormula;
import com.fimet.evaluator.function.internal.Function;
import com.fimet.evaluator.token.Token;
import com.fimet.evaluator.type.Expression;
import com.fimet.evaluator.type.TypeInteger;
import com.fimet.evaluator.type.TypeString;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class FLength extends Function {
	public FLength(ParserFormula parserFormula){
		super(parserFormula);
	}
	public FLength(ParserFormula parserFormula, Expression ... args){
		super(parserFormula, args);
	}
	public Expression parse(Token token, ParserFormula parser, int end) {
		args = parser.parseArgs(end);
		if (args == null || args.length != 1){
			throw new ParserFormulaException("Sintaxis invalida se esperaban 1 argumento length (arg1)");
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
	public TypeInteger eval(TypeString s){
		//return new TypeInteger(parserFormula.getMsg().getField(s.getValue()).getValue().length());
		Field field = parserFormula.getMsg().getField(s.getValue());
		return new TypeInteger(field == null ? -1 : field.getValue().length());
	}
	public Class<?> getType(){
		return com.fimet.evaluator.type.TypeInteger.class;
	}
	public String toString(){
		return "length('"+ ((TypeString)args[0]).getValue() + "')";
	}
}
