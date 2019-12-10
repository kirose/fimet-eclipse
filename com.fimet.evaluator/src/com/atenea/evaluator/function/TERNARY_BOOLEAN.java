package com.atenea.evaluator.function;

import com.atenea.evaluator.reader.ITokenReader;
import com.atenea.evaluator.token.TokenOperator;
import com.atenea.evaluator.token.TokenSeparator;
import com.atenea.evaluator.type.TypeBoolean;

public class TERNARY_BOOLEAN extends TypeBoolean {
	private TypeBoolean condition;
	private TypeBoolean option1;
	private TypeBoolean option2;
	public TERNARY_BOOLEAN(ITokenReader reader) {
		condition = reader.prevBoolean();
		reader.curr(TokenOperator.TERNARY);
		option1 = reader.nextBoolean();
		reader.next(TokenSeparator.COLON);
		option2 = reader.nextBoolean();
	}
	@Override
	public Boolean evaluate() {
		return condition.evaluate() ? option1.evaluate() : option2.evaluate();
	}
	@Override
	public Class<?> getType() {
		return option1.getType();
	}
}
