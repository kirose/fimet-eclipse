package com.atenea.evaluator.function;

import com.atenea.evaluator.reader.ITokenReader;
import com.atenea.evaluator.type.Type;
import com.atenea.evaluator.type.TypeBoolean;

public class EQUALS extends TypeBoolean {
	private Type left;
	private Type rigth;
	public EQUALS(ITokenReader reader) {
		left = reader.prevType();
		rigth = reader.nextType();
	}	
	@Override
	public Boolean evaluate() {
		return left.evaluate().equals(rigth.evaluate());
	}
	@Override
	public Class<?> getType() {
		return Boolean.class;
	}
}
