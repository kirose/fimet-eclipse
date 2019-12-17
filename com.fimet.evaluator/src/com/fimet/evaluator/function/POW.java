package com.fimet.evaluator.function;

import com.fimet.evaluator.reader.ITokenReader;
import com.fimet.evaluator.type.TypeNumber;

public class POW extends TypeNumber {
	private TypeNumber left;
	private TypeNumber rigth;
	public POW(ITokenReader reader) {
		left = reader.prevNumeric();
		rigth = reader.nextNumeric();
	}
	@Override
	public Double evaluate() {
		return left.evaluate().doubleValue() % rigth.evaluate().doubleValue();
	}
	@Override
	public Class<?> getType() {
		return left.getType() == rigth.getType() ? left.getType() : Double.class;
	}
}
