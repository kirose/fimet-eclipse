package com.fimet.evaluator.stmt;

import com.fimet.evaluator.ParserUtils;
import com.fimet.evaluator.reader.ITokenReader;
import com.fimet.evaluator.token.TokenGroup;
import com.fimet.evaluator.token.TokenSeparator;
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
