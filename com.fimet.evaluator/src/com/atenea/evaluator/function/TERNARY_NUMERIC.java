package com.atenea.evaluator.function;

import com.atenea.evaluator.reader.ITokenReader;
import com.atenea.evaluator.token.TokenOperator;
import com.atenea.evaluator.token.TokenSeparator;
import com.atenea.evaluator.type.TypeBoolean;
import com.atenea.evaluator.type.TypeNumber;

public class TERNARY_NUMERIC extends TypeNumber {
	private TypeBoolean condition;
	private TypeNumber option1;
	private TypeNumber option2;
	public TERNARY_NUMERIC(ITokenReader reader) {
		condition = reader.prevBoolean();
		reader.curr(TokenOperator.TERNARY);
		option1 = reader.nextNumeric();
		reader.next(TokenSeparator.COLON);
		option2 = reader.nextNumeric();
	}
	@Override
	public Number evaluate() {
		return condition.evaluate() ? option1.evaluate() : option2.evaluate();
	}
	@Override
	public Class<?> getType() {
		return option1.getType();
	}
}
