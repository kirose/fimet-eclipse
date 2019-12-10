package com.atenea.evaluator.function.internal.impl;


import com.atenea.commons.exception.ParserFormulaException;
import com.atenea.core.parser.field.Field;
import com.atenea.evaluator.ParserFormula;
import com.atenea.evaluator.function.internal.Function;
import com.atenea.evaluator.token.Token;
import com.atenea.evaluator.type.Expression;
import com.atenea.evaluator.type.TypeInteger;
import com.atenea.evaluator.type.TypeString;
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
		return com.atenea.evaluator.type.TypeInteger.class;
	}
	public String toString(){
		return "length('"+ ((TypeString)args[0]).getValue() + "')";
	}
}
