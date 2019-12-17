package com.fimet.evaluator.function;

import com.fimet.evaluator.reader.ITokenReader;
import com.fimet.evaluator.type.TypeBoolean;

public class AND extends TypeBoolean {
	private TypeBoolean left;
	private TypeBoolean rigth;
	public AND(ITokenReader reader) {
		left = reader.prevBoolean();
		rigth = reader.nextBoolean();
	}
	@Override
	public Boolean evaluate() {
		return left.evaluate() && rigth.evaluate();
	}
	@Override
	public Class<?> getType() {
		return Boolean.class;
	}
}
