package com.atenea.evaluator.stmt;

import com.atenea.evaluator.ParserUtils;
import com.atenea.evaluator.reader.ITokenReader;
import com.atenea.evaluator.token.TokenGroup;
import com.atenea.evaluator.token.TokenSeparator;
/**
 * 
 * @author <a href="mailto:marcoasb99@ciencias.unam.mx">Marco A. Salazar</a>
 * {[Statemetns]}
 */
public class ARGUMENTS extends Statement {
	Statement[] statements = new Statement[0];
	public ARGUMENTS(ITokenReader reader) {
		super(reader);
		reader.curr(TokenGroup.L_PARENTHESIS);
		while(reader.hasNext() && reader.peekNext() != TokenGroup.R_PARENTHESIS) {
			add(reader.nextStatement());
			if (reader.hasNext() && reader.peekNext() == TokenSeparator.COMMA) {
				reader.next();
			}
		}
		reader.next(TokenGroup.R_PARENTHESIS);
	}
	private void add(Statement stmt) {
		statements = ParserUtils.add(statements, stmt);
	}
	@Override
	public void invoke() {
		throw new NullPointerException();
	}

}
