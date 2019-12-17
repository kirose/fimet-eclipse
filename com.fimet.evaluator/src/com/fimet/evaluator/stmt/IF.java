package com.fimet.evaluator.stmt;


import com.fimet.evaluator.ParserUtils;
import com.fimet.evaluator.reader.ITokenReader;
import com.fimet.evaluator.token.TokenReserved;
import com.fimet.evaluator.type.TypeBoolean;
/**
 * 
 * @author <a href="mailto:marcoasb99@ciencias.unam.mx">Marco A. Salazar</a>
 *	if (condition1) {
 *		statements1...
 *	} else if (condition2) {
 *		statements2...
 *	} else {
 *		statements3...
 *	}
 */
public class IF extends Statement {
	private PAIR[] IFS = new PAIR[1];
	public IF(ITokenReader reader) {
		super(reader);
		add(new PAIR(reader.nextBoolean(), reader.nextStatement()));
		while (reader.peekNext() == TokenReserved.IF) {
			reader.next();
			add(new PAIR(reader.nextBoolean(), reader.nextStatement()));
		}
		if (reader.peekNext() == TokenReserved.ELSE) {
			reader.next();
			add(new PAIR(TypeBoolean.TRUE, reader.nextStatement()));
		}
	}

	private void add(PAIR block) {
		IFS = ParserUtils.add(IFS, block);
	}
	private class PAIR {
		TypeBoolean condition;
		Statement statement;
		PAIR(TypeBoolean condition, Statement statement){
			this.condition = condition;
			this.statement = statement;
		}
	}
	@Override
	public void invoke() {
		for (PAIR block : IFS) {
			if (block.condition.evaluate()) {
				block.statement.invoke();
				break;
			}
		}
	}
}
