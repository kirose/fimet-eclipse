package com.fimet.evaluator.function;

import com.fimet.evaluator.reader.ITokenReader;
import com.fimet.evaluator.token.TokenAlias;
import com.fimet.evaluator.token.TokenOperator;
import com.fimet.evaluator.type.Type;

public class MEMBER extends Type {
	private Type parent;
	private TokenAlias member;
	public MEMBER(ITokenReader reader) {
		parent = reader.prevType();
		reader.curr(TokenOperator.DOT);
		member = (TokenAlias)reader.next(TokenAlias.class);
	}
	@Override
	public Object evaluate() {
		return null;
	}
	@Override
	public Class<?> getType() {
		return null;
	}
}
