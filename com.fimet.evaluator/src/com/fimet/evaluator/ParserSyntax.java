package com.fimet.evaluator;

import com.fimet.evaluator.reader.ITokenReader;
import com.fimet.evaluator.token.Token;
import com.fimet.evaluator.token.TokenAlias;
import com.fimet.evaluator.token.TokenGroup;
import com.fimet.evaluator.token.TokenReserved;

public class ParserSyntax extends ParserLexical {
	public Program parseSyntax(ITokenReader reader) {
		Program program = new Program();
		Token token;
		while (reader.hasNext()) {
			token = reader.next();
			if (token instanceof TokenReserved) {
				TokenReserved reserved = (TokenReserved)token;
				if (reserved.isType()) {
					if (reader.peekNext() == TokenReserved.FUNCTION) {
						new Function
					}
				}
			} else if (token instanceof TokenAlias) {
				if (reader.peekNext() == TokenGroup.L_BRACKET) {// function
					new Func
				} else if ()
			} else {
				
			}
		}
		return null;
	}

}
