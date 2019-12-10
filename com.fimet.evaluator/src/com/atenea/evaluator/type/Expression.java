package com.atenea.evaluator.type;

import com.atenea.evaluator.ParserFormula;
import com.atenea.evaluator.token.Token;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public interface Expression {
	public Expression parse(Token token, ParserFormula parser, int end);
	public Expression eval();
	public Class<?> getType();
}
