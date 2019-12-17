package com.fimet.evaluator;


import com.fimet.commons.exception.ParserFormulaException;
import com.fimet.evaluator.reader.IReader;
import com.fimet.evaluator.reader.ITokenReader;
import com.fimet.evaluator.reader.StringReader;
import com.fimet.evaluator.reader.TokenReader;
import com.fimet.evaluator.token.TokenAlias;
import com.fimet.evaluator.token.TokenGroup;
import com.fimet.evaluator.token.TokenNumber;
import com.fimet.evaluator.token.TokenOperator;
import com.fimet.evaluator.token.TokenReserved;
import com.fimet.evaluator.token.TokenSeparator;
import com.fimet.evaluator.token.TokenString;

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
