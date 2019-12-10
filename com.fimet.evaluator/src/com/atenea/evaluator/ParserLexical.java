package com.atenea.evaluator;


import com.atenea.commons.exception.ParserFormulaException;
import com.atenea.evaluator.reader.IReader;
import com.atenea.evaluator.reader.ITokenReader;
import com.atenea.evaluator.reader.StringReader;
import com.atenea.evaluator.reader.TokenReader;
import com.atenea.evaluator.token.TokenAlias;
import com.atenea.evaluator.token.TokenGroup;
import com.atenea.evaluator.token.TokenNumber;
import com.atenea.evaluator.token.TokenOperator;
import com.atenea.evaluator.token.TokenReserved;
import com.atenea.evaluator.token.TokenSeparator;
import com.atenea.evaluator.token.TokenString;

public class ParserLexical {
	public ParserLexical() {}
	public ITokenReader parseTokens(String expression){
		if (expression == null || expression.trim().length() == 0) {
			return null;
		}
		IReader reader = new StringReader(expression);
		TokenReader tokenReader = new TokenReader();
		while (reader.hasNext()) {
			reader.nextNotBlank();
			if (reader.isSub() && tokenReader.isEmpty() && ParserUtils.isNumeric(reader.peekNextNotBlank())) {
				reader.nextNumeric();
				tokenReader.add(new TokenNumber("-"+reader.packNumeric()));
			} else if (reader.isAdd() && tokenReader.isEmpty() && ParserUtils.isNumeric(reader.peekNextNotBlank())) {
				reader.nextNumeric();
				tokenReader.add(new TokenNumber("+"+reader.packNumeric()));
			} else if (reader.isNumeric()) {
				tokenReader.add(new TokenNumber(reader.packNumeric()));
			} else if (reader.isAlpha()) {
				String name = reader.packAlphaNumeric();
				if (TokenReserved.isReserved(name)) {
					tokenReader.add(TokenReserved.getReserved(name));
				} else {
					tokenReader.add(new TokenAlias(name));
				}
			} else if (reader.isApostrophe()) {
				tokenReader.add(new TokenString(reader.packString()));
			} else if (reader.isAlpha()) {
				tokenReader.add(new TokenAlias(reader.packAlphaNumeric()));
			} else if (reader.isOperator()){
				tokenReader.add(TokenOperator.getOperator(reader.packOperator()));
			} else if (reader.isSeparator()){
				tokenReader.add(TokenSeparator.getSepatator(reader.packSeparator()));
			} else if (reader.isGroup()){
				tokenReader.add(TokenGroup.getGroup(reader.packGroup()));
			} else {
				throw new ParserFormulaException("Unexpected character '" + reader.curr() + "'");
			}
		}
		return tokenReader;
	}
}
