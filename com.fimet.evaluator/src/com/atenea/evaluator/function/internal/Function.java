package com.atenea.evaluator.function.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.atenea.commons.exception.ParserFormulaException;
import com.atenea.evaluator.ParserFormula;
import com.atenea.evaluator.token.Token;
import com.atenea.evaluator.type.Expression;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public abstract class Function implements Expression {
	final static Map<String,Class<?>> functions = new HashMap<String,Class<?>>();
	static{
		init();
	}
	protected ParserFormula parserFormula;
	protected Expression[] args;
	public static boolean isFunction(String token){
		return functions.containsKey(token);
	}
	public static Class<?> getFunction(String name){
		return functions.get(name);
	}
	public Function(ParserFormula parserFormula, Expression... args) {
		super();
		this.parserFormula = parserFormula;
		this.args = args;
	}
	public Function(ParserFormula parserFormula) {
		super();
		this.parserFormula = parserFormula;
	}
	public static void init(){
		functions.put("MIN", com.atenea.evaluator.function.internal.impl.MIN.class);
		functions.put("MAX", com.atenea.evaluator.function.internal.impl.MAX.class);
		functions.put("value", com.atenea.evaluator.function.internal.impl.FValue.class);
		functions.put("exists", com.atenea.evaluator.function.internal.impl.FExists.class);
		functions.put("length", com.atenea.evaluator.function.internal.impl.FLength.class);
		functions.put("substring", com.atenea.evaluator.function.internal.impl.FSubstring.class);
		functions.put("contains", com.atenea.evaluator.function.internal.impl.FContains.class);
		functions.put("matches", com.atenea.evaluator.function.internal.impl.FMatches.class);
		functions.put("indexOf", com.atenea.evaluator.function.internal.impl.FIndexOf.class);
		functions.put("isNumeric", com.atenea.evaluator.function.internal.impl.FIsNumeric.class);
		functions.put("isAlphabetic", com.atenea.evaluator.function.internal.impl.FIsAlphabetic.class);
		functions.put("isHexadecimal", com.atenea.evaluator.function.internal.impl.FIsHexadecimal.class);
		functions.put("in", com.atenea.evaluator.function.internal.impl.FIn.class);
	}
	public Class<?> getType(){
		return null;
	}
	public Expression[] getArgs() {
		return args;
	}
	public void setArgs(Expression[] args) {
		this.args = args;
	}
	public static Expression parseFunction(Token token, ParserFormula parser, int end){
		Class<?> clazzFn = getFunction(token.getValue());
		try {
			Constructor<?> constructor = clazzFn.getConstructor(ParserFormula.class);
			return (Expression)clazzFn
					.getMethod("parse", Token.class, ParserFormula.class, int.class)
					.invoke(constructor.newInstance(parser), token, parser, end);
		} catch (IllegalAccessException e) {
			throw new ParserFormulaException("No se puede acceder al metodo parse '" + token + "'",e);
		} catch (IllegalArgumentException e) {
			throw new ParserFormulaException("Argumentos invalidos al invocar parse '" + token + "'",e);
		} catch (InvocationTargetException e) {
			throw new ParserFormulaException(e);
		} catch (NoSuchMethodException e) {
			throw new ParserFormulaException("No existe el metodo parse de '" + token + "'",e);
		} catch (SecurityException e) {
			throw new ParserFormulaException("Error de seguridad parse '" + token + "'",e);
		} catch (InstantiationException e) {
			throw new ParserFormulaException("No es posible inistanciar operador '" + token + "'",e);
		}
	}
	public ParserFormula getParserFormula() {
		return parserFormula;
	}
	public void setParserFormula(ParserFormula parserFormula) {
		this.parserFormula = parserFormula;
	}
}