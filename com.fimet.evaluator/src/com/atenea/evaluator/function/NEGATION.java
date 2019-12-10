package com.atenea.evaluator.function;

import com.atenea.evaluator.reader.ITokenReader;
import com.atenea.evaluator.type.TypeBoolean;

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
