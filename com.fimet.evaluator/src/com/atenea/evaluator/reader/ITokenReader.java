package com.atenea.evaluator.reader;

import com.atenea.evaluator.stmt.Statement;
import com.atenea.evaluator.token.Token;
import com.atenea.evaluator.type.TypeBoolean;
import com.atenea.evaluator.type.TypeNumber;
import com.atenea.evaluator.type.TypeString;
import com.atenea.evaluator.type.Variable;
import com.atenea.evaluator.type.Type;

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
