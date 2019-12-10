package com.atenea.evaluator.stmt;

import com.atenea.evaluator.reader.ITokenReader;
import com.atenea.evaluator.token.TokenGroup;
import com.atenea.evaluator.token.TokenSeparator;
import com.atenea.evaluator.type.Type;
import com.atenea.evaluator.type.TypeBoolean;
/**
 * 
 * @author <a href="mailto:marcoasb99@ciencias.unam.mx">Marco A. Salazar</a>
 *	for(int i = 0; i < ln; i++){[Statements]}
 */
public class FOR extends Statement {
	private Type type1;
	private TypeBoolean condition;
	private Type type2;
	private Statement body;
	public FOR(ITokenReader reader) {
		super(reader);
		reader.next(TokenGroup.L_PARENTHESIS);
		type1 = reader.nextType();
		reader.next(TokenSeparator.SEMICOLON);
		condition = reader.nextBoolean();
		reader.next(TokenSeparator.SEMICOLON);
		type2 = reader.nextType();
		reader.next(TokenGroup.R_PARENTHESIS);
		body = reader.nextStatement();
	}

	@Override
	public void invoke() {
		for(type1.evaluate();condition.evaluate();type2.evaluate()) {
			body.invoke();
		}
	}

}
