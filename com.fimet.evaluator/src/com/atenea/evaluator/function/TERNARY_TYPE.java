package com.atenea.evaluator.function;

import com.atenea.commons.exception.TokenException;
import com.atenea.evaluator.reader.ITokenReader;
import com.atenea.evaluator.token.TokenOperator;
import com.atenea.evaluator.token.TokenSeparator;
import com.atenea.evaluator.type.Type;
import com.atenea.evaluator.type.TypeBoolean;

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
