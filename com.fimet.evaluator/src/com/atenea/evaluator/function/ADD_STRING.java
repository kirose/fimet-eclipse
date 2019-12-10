package com.atenea.evaluator.function;

import com.atenea.evaluator.reader.ITokenReader;
import com.atenea.evaluator.type.TypeString;

public class ADD_STRING extends TypeString {
	private TypeString left;
	private TypeString rigth;
	public ADD_STRING(ITokenReader reader) {
		left = reader.prevString();
		rigth = reader.nextString();
	}
	@Override
	public String evaluate() {
		return left.evaluate() + rigth.evaluate();
	}
	@Override
	public Class<?> getType() {
		return String.class;
	}
}
