package com.atenea.evaluator.function;

import com.atenea.evaluator.reader.ITokenReader;
import com.atenea.evaluator.stmt.BLOCK;
import com.atenea.evaluator.type.Type;

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
