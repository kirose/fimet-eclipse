package com.atenea.evaluator.token;

public class TokenReserved extends TokenAlias {

	private static TokenReserved[] reserved = new TokenReserved[0];
	public static TokenReserved FOR = new TokenReserved("for");
	public static TokenReserved WHILE = new TokenReserved("while");
	public static TokenReserved CONTINUE = new TokenReserved("continue");
	public static TokenReserved BREAK = new TokenReserved("break");
	public static TokenReserved IF = new TokenReserved("if");
	public static TokenReserved ELSE = new TokenReserved("else");
	public static TokenReserved RETURN = new TokenReserved("return");
	public static TokenReserved FUNCTION = new TokenReserved("function");
	public static TokenReserved IN = new TokenReserved("in");
	public static TokenReserved NEW = new TokenReserved("new");
	
	public static TokenReserved INTEGER = new TokenReserved("int");
	public static TokenReserved DOUBLE = new TokenReserved("double");
	public static TokenReserved STRING = new TokenReserved("string");
	public static TokenReserved OBJECT = new TokenReserved("object");
	public static TokenReserved VOID = new TokenReserved("void");
	
	public static TokenReserved TRUE = new TokenReserved("true");
	public static TokenReserved FALSE = new TokenReserved("false");
	
	public TokenReserved(String token) {
		super(token);
		add(this);
	}
	private static void add(TokenReserved token) {
		TokenReserved[] temp = new TokenReserved[reserved.length+1];
		System.arraycopy(reserved, 0, temp, 0, reserved.length);
		temp[reserved.length] = token;
		reserved = temp;
	}
	public static boolean isReserved(String name) {
		return getReserved(name) != null;
	}
	public static TokenReserved getReserved(String name) {
		for (TokenReserved token : reserved) {
			if (token.value.equals(name)) {
				return token;
			}
		}
		return null;
	}
	public boolean isType() {
		return this == INTEGER || this == DOUBLE || this == STRING || this == OBJECT;
	}
}
