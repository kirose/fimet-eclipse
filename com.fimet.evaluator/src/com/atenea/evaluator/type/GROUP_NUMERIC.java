package com.atenea.evaluator.type;

import com.atenea.evaluator.reader.ITokenReader;
import com.atenea.evaluator.token.TokenGroup;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class GROUP_NUMERIC extends TypeNumber {
	private TypeNumber expression;
	public GROUP_NUMERIC(ITokenReader reader) {
		reader.curr(TokenGroup.L_PARENTHESIS);
		expression = reader.nextNumeric();
		reader.curr(TokenGroup.R_PARENTHESIS);
	}
	public String toString(){
		return "(" + expression + ")";
	}
	public Class<?> getType() {
		return expression.getType();
	}
	@Override
	public Number evaluate() {
		return expression.evaluate();
	}
}
