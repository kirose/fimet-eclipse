package com.atenea.evaluator.function;

import com.atenea.evaluator.reader.ITokenReader;
import com.atenea.evaluator.type.TypeBoolean;
import com.atenea.evaluator.type.TypeNumber;

public class GREATER_EQUAL extends TypeBoolean {
	private TypeNumber left;
	private TypeNumber rigth;
	public GREATER_EQUAL(ITokenReader reader) {
		left = reader.prevNumeric();
		rigth = reader.nextNumeric();
	}
	@Override
	public Boolean evaluate() {
		return left.evaluate().doubleValue() >= rigth.evaluate().doubleValue();
	}
	@Override
	public Class<?> getType() {
		return Boolean.class;
	}
}
