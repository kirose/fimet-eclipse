package com.atenea.evaluator.token;

import com.atenea.commons.exception.TokenException;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class TokenOperator extends Token {
	private static TokenOperator[] operators = new TokenOperator[0];
	public static TokenOperator ADD = new TokenOperator("+");
	public static TokenOperator SUB = new TokenOperator("-");
	public static TokenOperator DIV = new TokenOperator("/");
	public static TokenOperator MUL = new TokenOperator("*");
	public static TokenOperator POW = new TokenOperator("^");
	public static TokenOperator MOD = new TokenOperator("%");
	public static TokenOperator LESS = new TokenOperator("<");
	public static TokenOperator LESS_EQ = new TokenOperator("<=");
	public static TokenOperator GREATER = new TokenOperator(">");
	public static TokenOperator GREATER_EQ = new TokenOperator(">=");
	public static TokenOperator EQUAL = new TokenOperator("==");
	public static TokenOperator DISTINCT = new TokenOperator("!=");
	public static TokenOperator FACTORIAL = new TokenOperator("!");
	public static TokenOperator AND = new TokenOperator("&&");
	public static TokenOperator OR = new TokenOperator("||");
	public static TokenOperator TERNARY = new TokenOperator("?");
	public static TokenOperator DOT = new TokenOperator(".");
	public static TokenOperator ASSIGNMENT = new TokenOperator("=");
	
	public TokenOperator(String token) {
		super(token);
		if (!isValid(token)) {
			throw new TokenException("Invalid Token Operator "+token);
		}
		add(this);
	}
	private static void add(TokenOperator token) {
		TokenOperator[] temp = new TokenOperator[operators.length+1];
		System.arraycopy(operators, 0, temp, 0, operators.length);
		temp[operators.length] = token;
		operators = temp;
	}
	public static boolean isOperator(String name) {
		return getOperator(name) != null;
	}
	public static TokenOperator getOperator(String name) {
		for (TokenOperator token : operators) {
			if (token.value.equals(name)) {
				return token;
			}
		}
		return null;
	} 
	private boolean isValid(String token) {
		return "+".equals(token) || "-".equals(token) || "/".equals(token) || 
			   "*".equals(token) || "^".equals(token) || "%".equals(token) ||
			   "<".equals(token) || "<=".equals(token) || ">".equals(token) ||
			   ">=".equals(token) || "==".equals(token) || "!=".equals(token) ||
			   "!".equals(token) || "&&".equals(token) || "||".equals(token) || 
			   "?".equals(token) || ":".equals(token) || ".".equals(token) ||
			   "=".equals(token); 
	}
}
