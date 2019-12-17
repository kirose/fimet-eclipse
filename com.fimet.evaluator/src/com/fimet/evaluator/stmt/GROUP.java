package com.fimet.evaluator.stmt;

import com.fimet.evaluator.ParserUtils;
import com.fimet.evaluator.reader.ITokenReader;
import com.fimet.evaluator.token.TokenGroup;
/**
 * 
 * @author <a href="mailto:marcoasb99@ciencias.unam.mx">Marco A. Salazar</a>
 * ([Statemetns])|[[Statement]]
 */
public class GROUP extends Statement {
	Statement[] statements = new Statement[0]; 
	public GROUP(ITokenReader reader) {
		super(reader);
		if (reader.curr() == TokenGroup.L_PARENTHESIS) {
			reader.curr(TokenGroup.L_PARENTHESIS);
			while(reader.hasNext() && reader.peekNext() != TokenGroup.R_PARENTHESIS) {
				add(reader.nextStatement());
			}
			reader.curr(TokenGroup.R_PARENTHESIS);	
		} else if (reader.curr() == TokenGroup.L_SQUARE_PARENTHESIS) {
			reader.curr(TokenGroup.L_SQUARE_PARENTHESIS);
			while(reader.hasNext() && reader.peekNext() != TokenGroup.R_SQUARE_PARENTHESIS) {
				add(reader.nextStatement());
			}
			reader.curr(TokenGroup.R_SQUARE_PARENTHESIS);
		}
		
	}
	private void add(Statement stmt) {
		statements = ParserUtils.add(statements, stmt);
	}
	@Override
	public void invoke() {
		throw new NullPointerException();
	}

}
