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
public class FSubstring extends Function {
	public FSubstring(ParserFormula parserFormula){
		super(parserFormula);
	}
	public FSubstring(ParserFormula parserFormula, Expression ... args){
		super(parserFormula, args);
	}
	public Expression parse(Token token, ParserFormula parser, int end) {
		args = parser.parseArgs(end);
		if (args == null || args.length != 3){
			throw new ParserFormulaException("Sintaxis invalida se esperaban 3 argumentos substring (String idField, int start, int end)");
		}
		return this;
	}
	public Expression eval(){
		Expression idField = args[0];
		Expression start = args[1];
		Expression end = args[2];
		if (!(idField instanceof TypeString)) {
			throw new ParserFormulaException("No existe la funcion eval (<"+idField.getClass()+","+start.getClass()+","+end.getClass()+ ">)");
		}
		if (!(start instanceof TypeInteger)) {
			throw new ParserFormulaException("No existe la funcion eval (<"+idField.getClass()+","+start.getClass()+","+end.getClass()+ ">)");
		}
		if (!(start instanceof TypeInteger)) {
			throw new ParserFormulaException("No existe la funcion eval (<"+idField.getClass()+","+start.getClass()+","+end.getClass()+ ">)");
		}
		return eval((TypeString)idField, (TypeInteger)start, (TypeInteger)end);
	}
	public TypeString eval(TypeString s, TypeInteger start, TypeInteger end){
		Field field = parserFormula.getMsg().getField(s.getValue());
		return new TypeString(field != null && field.getValue() != null ? field.getValue().substring(start.getValue(), end.getValue()) : null);
	}
	public Class<?> getType(){
		return TypeString.class;
	}
	public String toString(){
		return "substring('"+ ((TypeString)args[0]).getValue() +"',"+args[1].toString()+","+args[2].toString()+ ")";
	}
}
