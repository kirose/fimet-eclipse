package com.fimet.evaluator.reader;

import com.fimet.evaluator.stmt.Statement;
import com.fimet.evaluator.token.Token;
import com.fimet.evaluator.type.TypeBoolean;
import com.fimet.evaluator.type.TypeNumber;
import com.fimet.evaluator.type.TypeString;
import com.fimet.evaluator.type.Variable;
import com.fimet.evaluator.type.Type;

public interface ITokenReader {
	public boolean hasNext();
	public Token next(Class<? extends Token> classExpected);
	public Token next(Token expected);
	public Token next();
	public Token curr(Token expected);
	public Token curr();
	public Token prev(Token expected);
	public Token prev();
	public Token peekNext();
	public Token peekPrev();
	public Type nextType();
	public Type prevType();
	public TypeNumber nextNumeric();
	public TypeNumber prevNumeric();
	public TypeString nextString();
	public TypeString prevString();
	public TypeBoolean nextBoolean();
	public TypeBoolean prevBoolean();
	public Statement nextStatement();
	public Variable getVariable(String name);
	public void saveVariable(String name, Variable variable);
}
