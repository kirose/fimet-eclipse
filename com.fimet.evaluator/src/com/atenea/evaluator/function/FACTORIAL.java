package com.atenea.evaluator.function;

import com.atenea.evaluator.reader.ITokenReader;
import com.atenea.evaluator.token.TokenOperator;
import com.atenea.evaluator.type.TypeNumber;

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
