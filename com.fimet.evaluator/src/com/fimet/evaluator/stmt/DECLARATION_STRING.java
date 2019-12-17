package com.fimet.evaluator.stmt;

import com.fimet.evaluator.reader.ITokenReader;
import com.fimet.evaluator.token.TokenAlias;
import com.fimet.evaluator.token.TokenOperator;
import com.fimet.evaluator.token.TokenReserved;
import com.fimet.evaluator.type.Type;
import com.fimet.evaluator.type.TypeString;
import com.fimet.evaluator.type.Variable;

public class DECLARATION_STRING extends Statement {
	private Variable variable;
	String name;
	private TypeString expression;
	public DECLARATION_STRING(ITokenReader reader) {
		super(reader);
		reader.curr(TokenReserved.STRING);
		name = ((TokenAlias)reader.next(TokenAlias.class)).getValue();
		if (reader.peekNext() == TokenOperator.EQUAL) {
			reader.next(TokenOperator.EQUAL);
			expression = reader.nextString();
		}
	}
	@Override
	public void invoke() {
		variable = new Variable(String.class, name);
		//reader.saveVariable(name.getValue(), variable);
		if (expression != null) {
			
		}
		//if ()
		//variable.setValue(value.evaluate());
	}
}
