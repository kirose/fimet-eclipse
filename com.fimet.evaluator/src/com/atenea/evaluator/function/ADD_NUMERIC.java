package com.atenea.evaluator.function;

import com.atenea.evaluator.reader.ITokenReader;
import com.atenea.evaluator.token.TokenOperator;
import com.atenea.evaluator.type.TypeNumber;

public class ADD_NUMERIC extends TypeNumber {
	private TypeNumber left;
	private TypeNumber rigth;
	public ADD_NUMERIC(ITokenReader reader) {
		left = reader.prevNumeric();
		reader.curr(TokenOperator.ADD);
		rigth = reader.nextNumeric();
	}
	@Override
	public Double evaluate() {
		return left.evaluate().doubleValue() + rigth.evaluate().doubleValue();
	}
	@Override
	public Class<?> getType() {
		return left.getType() == rigth.getType() ? left.getType() : Double.class;
	}
}
