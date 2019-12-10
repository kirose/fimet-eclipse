package com.atenea.evaluator.function.internal.impl;

import com.atenea.commons.exception.ParserFormulaException;
import com.atenea.evaluator.ParserUtils;
import com.atenea.evaluator.function.internal.Function;
import com.atenea.evaluator.ParserFormula;
import com.atenea.evaluator.token.Token;
import com.atenea.evaluator.type.Expression;
import com.atenea.evaluator.type.TypeBoolean;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class FIn extends Function {
	private Expression value;
	public FIn(ParserFormula parserFormula){
		super(parserFormula);
	}
	public FIn(ParserFormula parserFormula, Expression ... args){
		super(parserFormula,args);
	}
	public Expression parse(Token token, ParserFormula parser, int end) {
		args = parser.parseArgs(end);
		value = parser.havePrevExpression() ? parser.getPrevExpression() :  null;
		if(value == null && args == null) {
			throw new ParserFormulaException("Sintaxis invalida se esperaban operandos izquierdo y derecho: <Expression> in (Expression, Expression, ...)" );
		} else if(args == null || args.length == 0){
			throw new ParserFormulaException("Sintaxis invalida se esperaban operando derecho: " + parser.getPrevExpression() +" in (Expression, Expression, ...)" );
		} else if(value == null){
			throw new ParserFormulaException("Sintaxis invalida se esperaban operando izquierdo: <Operando> in " + args);
		}
		return this;
	}
	public Expression eval(){
		Expression val = value.eval();
		Expression[] values = new Expression[args.length];
		for (int i = 0; i < values.length; i++) {
			if (val.equals(args[i].eval())) {
				return TypeBoolean.TRUE;
			}
		}
		return TypeBoolean.FALSE;
	}
	public Class<?> getType(){
		return TypeBoolean.class;
	}
	public String toString(){
		return value +" in ("+ ParserUtils.implode(args, ", ") + ")";
	}
}
