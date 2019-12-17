package com.fimet.evaluator.function;

import com.fimet.evaluator.reader.ITokenReader;
import com.fimet.evaluator.type.TypeBoolean;

public class NEGATION extends TypeBoolean {
	private TypeBoolean arg;
	public NEGATION(ITokenReader reader) {
		arg = reader.nextBoolean();
	}
	@Override
	public Boolean evaluate() {
		return arg.evaluate();
	}
	@Override
	public Class<?> getType() {
		return Boolean.class;
	}
}
