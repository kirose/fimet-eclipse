package com.atenea.evaluator.function;

import com.atenea.evaluator.reader.ITokenReader;
import com.atenea.evaluator.token.TokenAlias;
import com.atenea.evaluator.token.TokenOperator;
import com.atenea.evaluator.type.Type;

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
