package com.atenea.evaluator.function;

import com.atenea.evaluator.reader.ITokenReader;
import com.atenea.evaluator.token.TokenOperator;
import com.atenea.evaluator.token.TokenSeparator;
import com.atenea.evaluator.type.TypeBoolean;
import com.atenea.evaluator.type.TypeString;

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
