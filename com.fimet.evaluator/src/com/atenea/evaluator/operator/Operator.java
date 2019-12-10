package com.atenea.evaluator.operator;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.atenea.commons.exception.ParserFormulaException;
import com.atenea.evaluator.ParserFormula;
import com.atenea.evaluator.operator.impl.Add;
import com.atenea.evaluator.operator.impl.And;
import com.atenea.evaluator.operator.impl.Div;
import com.atenea.evaluator.operator.impl.Eq;
import com.atenea.evaluator.operator.impl.Ge;
import com.atenea.evaluator.operator.impl.Gt;
import com.atenea.evaluator.operator.impl.Le;
import com.atenea.evaluator.operator.impl.Lt;
import com.atenea.evaluator.operator.impl.Mod;
import com.atenea.evaluator.operator.impl.Mul;
import com.atenea.evaluator.operator.impl.Ne;
import com.atenea.evaluator.operator.impl.Not;
import com.atenea.evaluator.operator.impl.Or;
import com.atenea.evaluator.operator.impl.Pow;
import com.atenea.evaluator.operator.impl.Sub;
import com.atenea.evaluator.operator.impl.SubRight;
import com.atenea.evaluator.token.Token;
import com.atenea.evaluator.type.Expression;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public abstract class Operator implements Expression{
	protected final static int PRECEDENCE0 = 0;
	protected final static int PRECEDENCE1 = 1;
	protected final static int PRECEDENCE2 = 2;
	protected final static int PRECEDENCE3 = 3;
	protected final static int PRECEDENCE4 = 4;
	protected final static int PRECEDENCE5 = 5;
	protected final static int PRECEDENCE6 = 6;
	protected final static Map<String,Class<?>> operators = new HashMap<String,Class<?>>();
	static{
		init();
	}
	public abstract int getPrecedence();
	private static void init() {
		operators.put("+", Add.class);
		operators.put("-", Sub.class);
		operators.put("*", Mul.class);
		operators.put("/", Div.class);
		operators.put("%", Mod.class);
		operators.put("^", Pow.class);
		operators.put("==", Eq.class);
		operators.put("!=", Ne.class);
		operators.put(">", Gt.class);
		operators.put(">=", Ge.class);
		operators.put("<", Lt.class);
		operators.put("<=", Le.class);
		operators.put("&&", And.class);
		operators.put("||", Or.class);
		operators.put("!", Not.class);
	}
	public abstract String getName();
	public static Class<?> getOperator(String name, ParserFormula parser){
		if ("-".equals(name) && !parser.havePrevExpression()){
			return SubRight.class;
		}
		return operators.get(name);
	}
	public Expression parse(Token token, ParserFormula parser, int end) {
		return buildOperator(this, parser, end);
	}
	public static Expression parseOperator(Token token, ParserFormula parser, int end){
		try{
			Class<?> clazzOp = getOperator(token.getValue(), parser);
			return (Expression)clazzOp
					.getMethod("parse", Token.class, ParserFormula.class, int.class)
					.invoke(clazzOp.newInstance(), token, parser, end);
		} catch (IllegalAccessException e) {
			throw new ParserFormulaException("No se puede acceder al metodo parse '" + token + "'",e);
		} catch (IllegalArgumentException e) {
			throw new ParserFormulaException("Argumentos invalidos al invocar parse '" + token + "'",e);
		} catch (InvocationTargetException e) {
			throw new ParserFormulaException("Error al invocar parse '" + token + "'",e);
		} catch (NoSuchMethodException e) {
			throw new ParserFormulaException("No existe el metodo parse de '" + token + "'",e);
		} catch (SecurityException e) {
			throw new ParserFormulaException("Error de seguridad parse '" + token + "'",e);
		} catch (InstantiationException e) {
			throw new ParserFormulaException("No es posible inistanciar operador '" + token + "'",e);
		}
	}
	public static boolean isOperator(String token){
		return operators.containsKey(token);
	}
	protected Expression buildOperator(Operator op, ParserFormula parser, int end){
		parser.nextToken();
		if(op instanceof OperatorBinary){
			Expression left = parser.havePrevExpression() ? parser.getPrevExpression() :  null;
			Expression right = parser.parseExpression(end);
			if(left == null && right == null){
				throw new ParserFormulaException("Sintaxis invalida se esperaban operandos izquierdo y derecho: <Operando> "+ op.getName() + " <Operando>" );
			} else if(right == null){
				throw new ParserFormulaException("Sintaxis invalida se esperaban operando derecho: " + parser.getPrevExpression() +" "+ op.getName() + " <Operando>" );
			} else if(left == null){
				throw new ParserFormulaException("Sintaxis invalida se esperaban operando izquierdo: <Operando> "+ op.getName() + " " + right);
			}
			OperatorBinary ob = (OperatorBinary)op;
			ob.setLeft(left);
			if (right instanceof OperatorLeft && op.getPrecedence() >= ((Operator)right).getPrecedence()){
				return ((Operator)right).applyPrecedence(ob);
			} else {
				return ob.setRight(right);
			}
		} else if (op instanceof OperatorRight){
			Expression right = parser.parseExpression(end);
			if(right == null){
				throw new ParserFormulaException("Sintaxis invalida se esperaban operando derecho: " + op.getName() + " <Operando>" );
			}
			OperatorRight ob = (OperatorRight)op;
			if (right instanceof OperatorLeft && op.getPrecedence() >= ((Operator)right).getPrecedence()){
				return ((Operator)right).applyPrecedence(ob);
			} else {
				return ob.setRight(right);
			}
		} else if (op instanceof OperatorLeft){
			throw new ParserFormulaException("Nunca entrara aqui");
		} else {
			throw new ParserFormulaException("No se reconoce operador: " + op);
		}
	}
	public OperatorLeft applyPrecedence(OperatorRight opRight) {
		OperatorLeft opLeft = (OperatorLeft) this;
		if (opLeft.getLeft() instanceof OperatorLeft && opRight.getPrecedence() >= ((OperatorLeft)opLeft.getLeft()).getPrecedence()){
			((Operator)opLeft.getLeft()).applyPrecedence(opRight);
		} else {
			opRight.setRight(opLeft.getLeft());
			opLeft.setLeft(opRight);
		}
		return opLeft;
	}
}
