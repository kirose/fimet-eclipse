package com.fimet.evaluator.reader;

import com.fimet.commons.exception.TokenException;
import com.fimet.evaluator.function.ADD_NUMERIC;
import com.fimet.evaluator.function.DIV;
import com.fimet.evaluator.function.FACTORIAL;
import com.fimet.evaluator.function.MOD;
import com.fimet.evaluator.function.MUL;
import com.fimet.evaluator.function.POW;
import com.fimet.evaluator.function.SUB;
import com.fimet.evaluator.function.TERNARY_NUMERIC;
import com.fimet.evaluator.function.TERNARY_TYPE;
import com.fimet.evaluator.stmt.ASSIGNATION;
import com.fimet.evaluator.stmt.BLOCK;
import com.fimet.evaluator.stmt.FOR;
import com.fimet.evaluator.stmt.GROUP;
import com.fimet.evaluator.stmt.IF;
import com.fimet.evaluator.stmt.Statement;
import com.fimet.evaluator.stmt.WHILE;
import com.fimet.evaluator.token.Token;
import com.fimet.evaluator.token.TokenAlias;
import com.fimet.evaluator.token.TokenGroup;
import com.fimet.evaluator.token.TokenNumber;
import com.fimet.evaluator.token.TokenOperator;
import com.fimet.evaluator.token.TokenReserved;
import com.fimet.evaluator.token.TokenString;
import com.fimet.evaluator.type.DOUBLE;
import com.fimet.evaluator.type.GROUP_NUMERIC;
import com.fimet.evaluator.type.INTEGER;
import com.fimet.evaluator.type.STRING;
import com.fimet.evaluator.type.Type;
import com.fimet.evaluator.type.TypeBoolean;
import com.fimet.evaluator.type.TypeNumber;
import com.fimet.evaluator.type.TypeString;

public class TokenReader implements ITokenReader {
	ListTokens tokens = new ListTokens();
	public boolean isEmpty() {
		return tokens.isEmpty();
	}
	public void add(Token token) {
		tokens.add(token);
	}
	public boolean hasNext() {
		return tokens.hasNext();
	}
	public boolean hasPrev() {
		return tokens.hasPrev();
	}
	public Token curr() {
		return tokens.token();
	}
	public Token prev() {
		return tokens.prev().token();
	}
	public Token next() {
		return tokens.next().token();
	}
	@Override
	public Token next(Token expected) {
		Token token = tokens.next().token();
		if (token == expected) {
			return token;
		} else if (token.equals(expected)){
			return token;
		}
		throw new TokenException("Expected token "+expected);
	}
	@Override
	public Token next(Class<? extends Token> classExpected) {
		Token token = tokens.next().token();
		if (token.getClass() == classExpected) {
			return token;
		}
		throw new TokenException("Expected token "+classExpected.getSimpleName());
	}
	@Override
	public Token curr(Token expected) {
		Token token = tokens.token();
		if (token == expected) {
			return token;
		} else if (token.equals(expected)){
			return token;
		}
		throw new TokenException("Expected token "+expected);
	}
	@Override
	public Token prev(Token expected) {
		Token token = tokens.prev().token();
		if (token == expected) {
			return token;
		} else if (token.equals(expected)){
			return token;
		}
		throw new TokenException("Expected token "+expected);
	}
	public Token peekNext() {
		return tokens.curr.next != null ? tokens.curr.next.token : null;
	}
	public Token peekNextNext() {
		return tokens.curr.next != null && tokens.curr.next.next != null ? tokens.curr.next.next.token : null;
	}	
	public Token peekPrev() {
		return tokens.curr.prev != null ? tokens.curr.prev.token : null;
	}
	private void checkForNext() {
		if (!hasNext()) {
			throw new TokenException("Has not next");
		}
	}
	@Override
	public TypeNumber nextNumeric() {
		checkForNext();
		Token next = next();
		if (next instanceof TokenNumber) {
			if (next.getValue().matches("-?[0-9]+(.[0-9]*){0,1}")) {
				if (next.getValue().indexOf('.') != -1) {
					return new DOUBLE(Double.valueOf(next.getValue()));
				} else {
					return new INTEGER(Integer.valueOf(next.getValue()));			
				}
			} else {
				throw new TokenException("Expected token numeric insted "+next);
			}
		} else if (next == TokenGroup.L_PARENTHESIS) {
			return new GROUP_NUMERIC(this);
		} else if (next == TokenOperator.ADD) {
			return new ADD_NUMERIC(this);
		} else if (next == TokenOperator.SUB) {
			return new SUB(this);
		} else if (next == TokenOperator.MUL) {
			return new MUL(this);
		} else if (next == TokenOperator.DIV) {
			return new DIV(this);
		} else if (next == TokenOperator.POW) {
			return new POW(this);
		} else if (next == TokenOperator.SUB) {
			return new FACTORIAL(this);
		} else if (next == TokenOperator.MOD) {
			return new MOD(this);
		} else if (next == TokenOperator.TERNARY) {
			return new TERNARY_NUMERIC(this);
		} else if (next == TokenOperator.TERNARY) {
			return new TERNARY_NUMERIC(this);
		}
	
		throw new TokenException("Expected token numeric insted "+next);
	}
	@Override
	public TypeString nextString() {
		checkForNext();
		Token next = next();
		if (next instanceof TokenString) {
			return new STRING(next.getValue());
		} else {
			throw new TokenException("Expected token numeric insted "+next);
		}
	}
	@Override
	public TypeBoolean nextBoolean() {
		checkForNext();
		Token next = next();
		if (next == TokenReserved.TRUE) {
			return TypeBoolean.TRUE;
		} else if (next == TokenReserved.FALSE) {
			return TypeBoolean.FALSE;
		} else {
			throw new TokenException("Expected token numeric insted "+next);
		}
	}
	@Override
	public Statement nextStatement() {
		checkForNext();
		Token next = next();
		if (next == TokenGroup.L_BRACKET) {
			return new BLOCK(this);
		} else if (next == TokenGroup.L_PARENTHESIS) {
			return new GROUP(this);
		} else if (next == TokenGroup.L_SQUARE_PARENTHESIS) {
			return new GROUP(this);
		} else if (next instanceof TokenReserved) {
			if (next == TokenReserved.FOR) {
				return new FOR(this);
			} else if (next == TokenReserved.IF) {
				return new IF(this);
			} else if (next == TokenReserved.WHILE) {
				return new WHILE(this);
			} else if (((TokenReserved)next).isType() && peekNext() instanceof TokenAlias && peekNextNext() == TokenOperator.EQUAL) {
				return new ASSIGNATION(this);	
			}
		} else if (next instanceof TokenAlias && peekNext() instanceof TokenAlias && peekNextNext() == TokenOperator.EQUAL) {
			return new ASSIGNATION(this);
		}
		return null;
	}
	public BLOCK nextBlock() {
		return null;
	}
	@Override
	public Type nextType() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Type prevType() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public TypeNumber prevNumeric() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public TypeString prevString() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public TypeBoolean prevBoolean() {
		// TODO Auto-generated method stub
		return null;
	}
	private class ListTokens {
		Node head = new Node();
		Node tail = head;
		Node curr = head;
		int size = 0;
		public boolean isEmpty() {
			return size > 0;
		}
		public void add(Token token) {
			tail.next = new Node(token, tail);
			size++;
		}
		public boolean hasNext() {
			return curr.next != null;
		}
		public boolean hasPrev() {
			return curr.prev != null;
		}
		public ListTokens next() {
			curr = curr.next;
			return this;
		}
		public Token token() {
			return curr.token;
		}
		public ListTokens prev() {
			curr = curr.prev;
			return this;
		}
	}
	private class Node {
		Token token;
		Node prev;
		Node next;
		public Node() {}
		public Node(Token token, Node prev) {
			super();
			this.token = token;
			this.prev = prev;
		}
	}

}
