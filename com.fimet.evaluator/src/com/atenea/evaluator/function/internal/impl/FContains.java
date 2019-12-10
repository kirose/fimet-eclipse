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
public class FContains extends Function {
	public FContains(ParserFormula parserFormula){
		super(parserFormula);
	}
	public FContains(ParserFormula parserFormula, Expression ... args){
		super(parserFormula, args);
	}
	public Expression parse(Token token, ParserFormula parser, int end) {
		args = parser.parseArgs(end);
		if (args == null || args.length != 2){
			throw new ParserFormulaException("Sintaxis invalida se esperaban 2 argumentos substring (String idField, String text)");
		}
		return this;
	}
	public Expression eval(){
		Expression idField = args[0];
		Expression text = args[1];
		if (!(idField instanceof TypeString)) {
			throw new ParserFormulaException("No existe la funcion eval (<"+idField.getClass()+","+text.getClass()+ ">)");
		}
		if (!(text instanceof TypeString)) {
			throw new ParserFormulaException("No existe la funcion eval (<"+idField.getClass()+","+text.getClass()+ ">)");
		}
		return eval((TypeString)idField, (TypeString)text);
	}
	public TypeBoolean eval(TypeString s, TypeString text){
		Field field = parserFormula.getMsg().getField(s.getValue());
		return new TypeBoolean(field != null && field.getValue() != null ? field.getValue().contains(text.getValue()) : false);
	}
	public Class<?> getType(){
		return TypeBoolean.class;
	}
	public String toString(){
		return "contains('"+ ((TypeString)args[0]).getValue() +"','"+args[1].toString()+"')";
	}
}
