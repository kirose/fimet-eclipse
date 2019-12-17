package com.fimet.evaluator.function;

import com.fimet.evaluator.reader.ITokenReader;
import com.fimet.evaluator.token.TokenOperator;
import com.fimet.evaluator.token.TokenSeparator;
import com.fimet.evaluator.type.TypeBoolean;
import com.fimet.evaluator.type.TypeString;

public class TERNARY_STRING extends TypeString {
	private TypeBoolean condition;
	private TypeString option1;
	private TypeString option2;
	public TERNARY_STRING(ITokenReader reader) {
		condition = reader.prevBoolean();
		reader.curr(TokenOperator.TERNARY);
		option1 = reader.nextString();
		reader.next(TokenSeparator.COLON);
		option2 = reader.nextString();
	}
	@Override
	public String evaluate() {
		return condition.evaluate() ? option1.evaluate() : option2.evaluate();
	}
	@Override
	public Class<?> getType() {
		return option1.getType();
	}
}
