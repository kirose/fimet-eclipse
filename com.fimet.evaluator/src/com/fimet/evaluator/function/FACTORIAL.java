package com.fimet.evaluator.function;

import com.fimet.evaluator.reader.ITokenReader;
import com.fimet.evaluator.token.TokenOperator;
import com.fimet.evaluator.type.TypeNumber;

public class FACTORIAL extends TypeNumber {
	private TypeNumber number;
	public FACTORIAL(ITokenReader reader) {
		reader.curr(TokenOperator.FACTORIAL);
		number = reader.prevNumeric();
	}
	@Override
	public Double evaluate() {
		return number.evaluate().doubleValue();
	}
	@Override
	public Class<?> getType() {
		return number.getType();
	}
}
