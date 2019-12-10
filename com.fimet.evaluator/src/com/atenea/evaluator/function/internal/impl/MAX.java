package com.atenea.evaluator.function.internal.impl;

import java.lang.reflect.InvocationTargetException;

import com.atenea.commons.exception.ParserFormulaException;
import com.atenea.evaluator.ParserUtils;
import com.atenea.evaluator.function.internal.Function;
import com.atenea.evaluator.ParserFormula;
import com.atenea.evaluator.token.Token;
import com.atenea.evaluator.type.Expression;
import com.atenea.evaluator.type.TypeDouble;
import com.atenea.evaluator.type.TypeInteger;
import com.atenea.evaluator.type.TypeNumber;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class MAX extends Function {
	public MAX(ParserFormula parserFormula){
		super(parserFormula);
	}
	public MAX(ParserFormula parserFormula,Expression ... args){
		super(parserFormula,args);
	}
	public Expression parse(Token token, ParserFormula parser, int end) {
		args = parser.parseArgs(end);
		if (args == null || args.length != 2){
			throw new ParserFormulaException("Sintaxis invalida se esperaban 2 argumrntos MAX (arg1 number, arg2 number)");
		}
		return this;
	}
	public Expression eval(){
		Expression l = args[0].eval();
		Expression r = args[1].eval();
		try {
			return (Expression) this.getClass().getMethod("eval", l.getClass(), r.getClass()).invoke(this, l, r);
		} catch (IllegalAccessException e) {
			throw new ParserFormulaException("No existe el operador MAX (<"+l.getClass()+">, <" + r.getClass() + ">)");
		} catch (IllegalArgumentException e) {
			throw new ParserFormulaException("No existe el operador MAX (<"+l.getClass()+">, <" + r.getClass() + ">)");
		} catch (InvocationTargetException e) {
			throw new ParserFormulaException("No existe el operador MAX (<"+l.getClass()+">, <" + r.getClass() + ">)");
		} catch (NoSuchMethodException e) {
			throw new ParserFormulaException("No existe el operador MAX (<"+l.getClass()+">, <" + r.getClass() + ">)");
		} catch (SecurityException e) {
			throw new ParserFormulaException("No existe el operador MAX (<"+l.getClass()+">, <" + r.getClass() + ">)");
		}

	}
	public TypeDouble eval(TypeDouble v0, TypeDouble v1){
		return new TypeDouble(Math.max(v0.getValue(), v1.getValue()));
	}
	public TypeInteger eval(TypeInteger v0, TypeInteger v1){
		return new TypeInteger(Math.max(v0.getValue(), v1.getValue()));
	}
	public TypeDouble eval(TypeDouble v0, TypeInteger v1){
		return new TypeDouble(Math.max(v0.getValue(), v1.getValue()));
	}
	public TypeDouble eval(TypeInteger v0, TypeDouble v1){
		return new TypeDouble(Math.max(v0.getValue(), v1.getValue()));
	}
	public Class<?> getType(){
		return TypeNumber.class;
	}
	public String toString(){
		return "greatest(" + ParserUtils.implode(args, ", ") + ")";
	}

}
