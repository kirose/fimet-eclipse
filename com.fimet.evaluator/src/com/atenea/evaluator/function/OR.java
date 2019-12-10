package com.atenea.evaluator.function;

import com.atenea.evaluator.reader.ITokenReader;
import com.atenea.evaluator.type.TypeBoolean;

public class OR extends TypeBoolean {
	private TypeBoolean left;
	private TypeBoolean rigth;
	public OR(ITokenReader reader) {
		left = reader.prevBoolean();
		rigth = reader.nextBoolean();
	}
	@Override
	public Boolean evaluate() {
		return left.evaluate() || rigth.evaluate();
	}
	@Override
	public Class<?> getType() {
		return Boolean.class;
	}
}
