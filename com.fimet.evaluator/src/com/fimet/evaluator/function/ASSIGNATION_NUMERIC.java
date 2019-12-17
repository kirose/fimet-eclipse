package com.fimet.evaluator.function;

import com.fimet.evaluator.reader.ITokenReader;
import com.fimet.evaluator.token.TokenAlias;
import com.fimet.evaluator.token.TokenOperator;
import com.fimet.evaluator.type.TypeNumber;
import com.fimet.evaluator.type.Variable;

public class ASSIGNATION_NUMERIC extends TypeNumber {

	private Variable variable;
	private TypeNumber expression;
	public ASSIGNATION_NUMERIC(ITokenReader reader) {
		TokenAlias name = (TokenAlias)reader.next(TokenAlias.class);
		reader.curr(TokenOperator.EQUAL);
		expression = reader.nextNumeric();
		variable = reader.getVariable(name.getValue());
	}
	@Override
	public Number evaluate() {
		Number evaluate = expression.evaluate();
		variable.setValue(evaluate);
		return evaluate;
	}
	@Override
	public Class<?> getType() {
		return variable.getType();
	}
}
