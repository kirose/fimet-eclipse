package com.atenea.evaluator.stmt;

import com.atenea.evaluator.reader.ITokenReader;

public abstract class Statement {
	public Statement (ITokenReader reader) {
	}
	abstract public void invoke();
}
