package com.atenea.evaluator.function;

import com.atenea.evaluator.reader.ITokenReader;
import com.atenea.evaluator.type.TypeNumber;

public class MOD extends TypeNumber {
	private TypeNumber left;
	private TypeNumber rigth;
	public MOD(ITokenReader reader) {
		left = reader.prevNumeric();
		rigth = reader.nextNumeric();
	}
	@Override
	public Double evaluate() {
		return Math.pow(left.evaluate().doubleValue(), rigth.evaluate().doubleValue());
	}
	@Override
	public Class<?> getType() {
		return left.getType() == rigth.getType() ? left.getType() : Double.class;
	}
}
