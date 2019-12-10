package com.atenea.evaluator.stmt;

import com.atenea.evaluator.reader.ITokenReader;
import com.atenea.evaluator.token.TokenAlias;
import com.atenea.evaluator.token.TokenOperator;
import com.atenea.evaluator.token.TokenReserved;
import com.atenea.evaluator.type.Type;
import com.atenea.evaluator.type.TypeString;
import com.atenea.evaluator.type.Variable;

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
