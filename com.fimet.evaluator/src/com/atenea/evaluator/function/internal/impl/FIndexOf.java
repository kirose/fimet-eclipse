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
public class FIndexOf extends Function {
	public FIndexOf(ParserFormula parserFormula){
		super(parserFormula);
	}
	public FIndexOf(ParserFormula parserFormula, Expression ... args){
		super(parserFormula, args);
	}
	public Expression parse(Token token, ParserFormula parser, int end) {
		args = parser.parseArgs(end);
		if (args == null || args.length != 2){
			throw new ParserFormulaException("Sintaxis invalida se esperaban 2 argumentos indexOf (String idField, String text)");
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
	public TypeInteger eval(TypeString s, TypeString text){
		Field field = parserFormula.getMsg().getField(s.getValue());
		return new TypeInteger(field != null && field.getValue() != null ? field.getValue().indexOf(text.getValue()) : -1);
	}
	public Class<?> getType(){
		return TypeInteger.class;
	}
	public String toString(){
		return "contains('"+ ((TypeString)args[0]).getValue() +"','"+args[1].toString()+"')";
	}
}
