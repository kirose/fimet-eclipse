package com.fimet.evaluator.function;

import com.fimet.commons.exception.TokenException;
import com.fimet.evaluator.reader.ITokenReader;
import com.fimet.evaluator.token.TokenOperator;
import com.fimet.evaluator.token.TokenSeparator;
import com.fimet.evaluator.type.Type;
import com.fimet.evaluator.type.TypeBoolean;

public class TERNARY_TYPE extends Type {
	private TypeBoolean condition;
	private Type option1;
	private Type option2;
	public TERNARY_TYPE(ITokenReader reader) {
		condition = reader.prevBoolean();
		reader.curr(TokenOperator.TERNARY);
		option1 = reader.nextType();
		reader.next(TokenSeparator.COLON);
		option2 = reader.nextType();
		if (option1.getType() != option2.getType()) {
			throw new TokenException("The arguments type for the ternary operator must be the same");
		}
	}
	@Override
	public Object evaluate() {
		return condition.evaluate() ? option1.evaluate() : option2.evaluate();
	}
	@Override
	public Class<?> getType() {
		return option1.getType();
	}
}
