package com.fimet.evaluator.stmt;

import com.fimet.evaluator.reader.ITokenReader;
import com.fimet.evaluator.type.TypeBoolean;

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
