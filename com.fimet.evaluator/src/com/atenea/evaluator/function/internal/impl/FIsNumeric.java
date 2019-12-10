package com.atenea.evaluator.function.internal.impl;

import com.atenea.commons.exception.ParserFormulaException;
import com.atenea.core.parser.field.Field;
import com.atenea.evaluator.ParserFormula;
import com.atenea.evaluator.function.internal.Function;
import com.atenea.evaluator.token.Token;
import com.atenea.evaluator.type.Expression;
import com.atenea.evaluator.type.TypeBoolean;
import com.atenea.evaluator.type.TypeString;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class FIsNumeric extends Function {
	public FIsNumeric(ParserFormula parserFormula){
		super(parserFormula);
	}
	public FIsNumeric(ParserFormula parserFormula, Expression ... args){
		super(parserFormula, args);
	}
	public Expression parse(Token token, ParserFormula parser, int end) {
		args = parser.parseArgs(end);
		if (args == null || args.length != 1){
			throw new ParserFormulaException("Sintaxis invalida se esperaban 1 argumento isNumeric (String idField)");
		}
		return this;
	}
	public Expression eval(){
		Expression idField = args[0];
		if (!(idField instanceof TypeString)) {
			throw new ParserFormulaException("No existe la funcion eval (<"+idField.getClass()+ ">)");
		}
		return eval((TypeString)idField);
	}
	public TypeBoolean eval(TypeString s){
		Field field = parserFormula.getMsg().getField(s.getValue());
		return new TypeBoolean(field != null && field.getValue() != null ? field.getValue().matches("[0-9]+") : false);
	}
	public Class<?> getType(){
		return TypeBoolean.class;
	}
	public String toString(){
		return "matches('"+ ((TypeString)args[0]).getValue() +"','"+args[1].toString()+"')";
	}
}
