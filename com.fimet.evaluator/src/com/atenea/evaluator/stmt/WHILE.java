package com.atenea.evaluator.stmt;

import com.atenea.evaluator.reader.ITokenReader;
import com.atenea.evaluator.type.TypeBoolean;

public class WHILE extends Statement {
	private TypeBoolean condition;
	private Statement statement;
	public WHILE(ITokenReader reader) {
		super(reader);
		reader.next();
		condition = reader.nextBoolean();
		statement = reader.nextStatement();
	}
	@Override
	public void invoke() {
		while(condition.evaluate()) {
			statement.invoke();
		}
	}
}
