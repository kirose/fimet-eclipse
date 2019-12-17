package com.fimet.evaluator.function;

import com.fimet.evaluator.reader.ITokenReader;
import com.fimet.evaluator.token.TokenAlias;
import com.fimet.evaluator.token.TokenOperator;
import com.fimet.evaluator.type.TypeString;
import com.fimet.evaluator.type.Variable;

public class ASSIGNATION_STRING extends TypeString {

	private Variable variable;
	private TypeString expression;
	public ASSIGNATION_STRING(ITokenReader reader) {
		TokenAlias name = (TokenAlias)reader.next(TokenAlias.class);
		reader.curr(TokenOperator.EQUAL);
		expression = reader.nextString();
		variable = reader.getVariable(name.getValue());
	}
	@Override
	public String evaluate() {
		String evaluate = expression.evaluate();
		variable.setValue(evaluate);
		return evaluate;
	}
	@Override
	public Class<?> getType() {
		return variable.getType();
	}
}
