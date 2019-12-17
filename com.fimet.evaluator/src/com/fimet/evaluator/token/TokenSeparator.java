package com.fimet.evaluator.token;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class TokenSeparator extends Token {

	private static TokenSeparator[] sepatators = new TokenSeparator[0];
	public final static TokenSeparator COMMA = new TokenSeparator(",");
	public final static TokenSeparator SEMICOLON = new TokenSeparator(";");
	public final static TokenSeparator COLON = new TokenSeparator(":");

	public TokenSeparator(String token) {
		super(token);
		add(this);
	}
	private static void add(TokenSeparator token) {
		TokenSeparator[] temp = new TokenSeparator[sepatators.length+1];
		System.arraycopy(sepatators, 0, temp, 0, sepatators.length);
		temp[sepatators.length] = token;
		sepatators = temp;
	}
	public static boolean isSepatator(String name) {
		return getSepatator(name) != null;
	}
	public static TokenSeparator getSepatator(String name) {
		for (TokenSeparator token : sepatators) {
			if (token.value.equals(name)) {
				return token;
			}
		}
		return null;
	} 
}
