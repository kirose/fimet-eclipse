package com.atenea.evaluator.function;

import com.atenea.evaluator.reader.ITokenReader;
import com.atenea.evaluator.token.TokenAlias;
import com.atenea.evaluator.token.TokenOperator;
import com.atenea.evaluator.type.TypeBoolean;
import com.atenea.evaluator.type.Variable;

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
