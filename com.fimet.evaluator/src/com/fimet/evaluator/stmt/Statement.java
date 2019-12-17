package com.fimet.evaluator.stmt;

import com.fimet.evaluator.reader.ITokenReader;

public abstract class Statement {
	public Statement (ITokenReader reader) {
	}
	abstract public void invoke();
}
