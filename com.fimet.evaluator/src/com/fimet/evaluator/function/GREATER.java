package com.fimet.evaluator.function;

import com.fimet.evaluator.reader.ITokenReader;
import com.fimet.evaluator.type.TypeBoolean;
import com.fimet.evaluator.type.TypeNumber;

public class GREATER extends TypeBoolean {
	private TypeNumber left;
	private TypeNumber rigth;
	public GREATER(ITokenReader reader) {
		left = reader.prevNumeric();
		rigth = reader.nextNumeric();
	}
	@Override
	public Boolean evaluate() {
		return left.evaluate().doubleValue() > rigth.evaluate().doubleValue();
	}
	@Override
	public Class<?> getType() {
		return Boolean.class;
	}
}
