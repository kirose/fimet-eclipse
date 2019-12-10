package com.atenea.evaluator.type;

import com.atenea.evaluator.reader.ITokenReader;
import com.atenea.evaluator.token.TokenGroup;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class GROUP_STRING extends TypeString {
	private TypeString expression;
	public GROUP_STRING(ITokenReader reader) {
		reader.curr(TokenGroup.L_PARENTHESIS);
		expression = reader.nextString();
		reader.curr(TokenGroup.R_PARENTHESIS);
	}
	public String toString(){
		return "(" + expression + ")";
	}
	public Class<?> getType() {
		return expression.getType();
	}
	@Override
	public String evaluate() {
		return expression.evaluate();
	}
}
