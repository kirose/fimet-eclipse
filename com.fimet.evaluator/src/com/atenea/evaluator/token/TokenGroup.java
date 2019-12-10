package com.atenea.evaluator.token;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class TokenGroup extends Token {

	private static TokenGroup[] groups = new TokenGroup[0];
	public final static TokenGroup L_PARENTHESIS = new TokenGroup("(");
	public final static TokenGroup R_PARENTHESIS = new TokenGroup(")");
	public final static TokenGroup L_SQUARE_PARENTHESIS = new TokenGroup("[");
	public final static TokenGroup R_SQUARE_PARENTHESIS = new TokenGroup("]");
	public final static TokenGroup L_BRACKET = new TokenGroup("{");
	public final static TokenGroup R_BRACKET = new TokenGroup("}");
	
	public TokenGroup(String token) {
		super(token);
		add(this);
	}
	private static void add(TokenGroup token) {
		TokenGroup[] temp = new TokenGroup[groups.length+1];
		System.arraycopy(groups, 0, temp, 0, groups.length);
		temp[groups.length] = token;
		groups = temp;
	}
	public static boolean isGroup(String name) {
		return getGroup(name) != null;
	}
	public static TokenGroup getGroup(String name) {
		for (TokenGroup token : groups) {
			if (token.value.equals(name)) {
				return token;
			}
		}
		return null;
	} 
}
