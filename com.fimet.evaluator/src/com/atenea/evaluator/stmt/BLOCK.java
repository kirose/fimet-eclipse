package com.atenea.evaluator.stmt;

import com.atenea.evaluator.ParserUtils;
import com.atenea.evaluator.reader.ITokenReader;
import com.atenea.evaluator.token.TokenGroup;
/**
 * 
 * @author <a href="mailto:marcoasb99@ciencias.unam.mx">Marco A. Salazar</a>
 * {[Statemetns]}
 */
public class BLOCK extends Statement {
	Statement[] statements = new Statement[0]; 
	public BLOCK(ITokenReader reader) {
		super(reader);
		reader.curr(TokenGroup.L_BRACKET);
		while(reader.hasNext() && reader.peekNext() != TokenGroup.R_BRACKET) {
			add(reader.nextStatement());
		}
		reader.curr(TokenGroup.R_BRACKET);
	}
	private void add(Statement stmt) {
		statements = ParserUtils.add(statements, stmt);
	}
	@Override
	public void invoke() {
		throw new NullPointerException();
	}

}
