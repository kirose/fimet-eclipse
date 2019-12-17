package com.fimet.evaluator.function;

import com.fimet.evaluator.reader.ITokenReader;
import com.fimet.evaluator.token.TokenAlias;
import com.fimet.evaluator.token.TokenOperator;
import com.fimet.evaluator.type.TypeBoolean;
import com.fimet.evaluator.type.Variable;

public class ASSIGNATION_BOOLEAN extends TypeBoolean {

	private Variable variable;
	private TypeBoolean expression;
	public ASSIGNATION_BOOLEAN(ITokenReader reader) {
		TokenAlias name = (TokenAlias)reader.next(TokenAlias.class);
		reader.curr(TokenOperator.EQUAL);
		expression = reader.nextBoolean();
		variable = reader.getVariable(name.getValue());
	}
	@Override
	public Boolean evaluate() {
		Boolean evaluate = expression.evaluate();
		variable.setValue(evaluate);
		return evaluate;
	}
	@Override
	public Class<?> getType() {
		return variable.getType();
	}
}
