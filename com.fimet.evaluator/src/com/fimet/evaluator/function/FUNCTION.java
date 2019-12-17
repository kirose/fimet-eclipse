package com.fimet.evaluator.function;

import com.fimet.evaluator.reader.ITokenReader;
import com.fimet.evaluator.stmt.BLOCK;
import com.fimet.evaluator.type.Type;

/**
 * 
 * @author <a href="mailto:marcoasb99@ciencias.unam.mx">Marco A. Salazar</a>
 *
 *	void main(string[] args){...}
 */
public class FUNCTION {
	private Type returnType;
	private Type[] arguments;
	private BLOCK body;
	public FUNCTION(ITokenReader reader) {
		
	}
}
