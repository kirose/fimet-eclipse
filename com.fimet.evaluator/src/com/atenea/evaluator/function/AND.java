package com.atenea.evaluator.function;

import com.atenea.evaluator.reader.ITokenReader;
import com.atenea.evaluator.type.TypeBoolean;

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
