package com.fimet.evaluator.function;

import com.fimet.evaluator.reader.ITokenReader;
import com.fimet.evaluator.token.TokenAlias;
import com.fimet.evaluator.token.TokenOperator;
import com.fimet.evaluator.type.Type;
import com.fimet.evaluator.type.Variable;

public class ASSIGNATION_TYPE extends Type {

	private Variable variable;
	private Type expression;
	public ASSIGNATION_TYPE(ITokenReader reader) {
		TokenAlias name = (TokenAlias)reader.next(TokenAlias.class);
		reader.curr(TokenOperator.EQUAL);
		expression = reader.nextType();
		variable = reader.getVariable(name.getValue());
	}
	@Override
	public Object evaluate() {
		Object evaluate = expression.evaluate();
		variable.setValue(evaluate);
		return evaluate;
	}
	@Override
	public Class<?> getType() {
		return variable.getType();
	}
}
