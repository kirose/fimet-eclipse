package com.atenea.evaluator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.atenea.commons.exception.ParserFormulaException;
import com.atenea.core.parser.field.Message;
import com.atenea.evaluator.function.internal.Function;
import com.atenea.evaluator.operator.Operator;
import com.atenea.evaluator.token.Token;
import com.atenea.evaluator.token.TokenAlias;
import com.atenea.evaluator.token.TokenGroup;
import com.atenea.evaluator.token.TokenNumber;
import com.atenea.evaluator.token.TokenOperator;
import com.atenea.evaluator.token.TokenString;
import com.atenea.evaluator.type.Alias;
import com.atenea.evaluator.type.Expression;
import com.atenea.evaluator.type.GROUP_NUMERIC;
import com.atenea.evaluator.type.TypeBoolean;
import com.atenea.evaluator.type.TypeNumber;
import com.atenea.evaluator.type.TypeString;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class ParserFormula {
	private Message msg;
	ParserReader reader;
	List<Token> tokens;
	int sizeTokens;
	int index;
	java.util.Stack<Expression> prevExpressions;
	Expression currExpression;
	Expression root;

	public ParserFormula(){
		this(null);
	}
	public ParserFormula(Message msg){
		this.msg = msg;
		this.tokens = new LinkedList<Token>();
		this.prevExpressions = new java.util.Stack<Expression>();
	}
	public void validateSyntax(){
	}
	public void validateSemantic(){
		
	}
	public ParserFormula parse(String strFormula){
		//System.out.println(" *************  Parse Formula  ************ ");
		reader = new ParserReader(strFormula);
		//System.out.println("Formula: " + reader);
		buildTokens();
		//System.out.println("Tokens: " + tokens);
		buildTree();
		//System.out.println("Tree: "+root);
		validateSyntax();
		validateSemantic();
		return this;
	}
	public Expression eval(){
		Expression eval = root.eval();
		//System.out.println("Eval: " + eval);
		return eval;
	}
	public Expression eval(String formula){
		if (root == null){
			parse(formula);
		}
		Expression eval = root.eval();
		System.out.println("Formula: " + reader);
		System.out.println("Tokens: " + tokens);
		System.out.println("Tree: "+root);
		System.out.println("Eval: " + eval);
		return eval;
	}
	public Token getToken(int index){
		if (index >= 0 && index < tokens.size()){
			return tokens.get(index-1);	
		}
		return null;
	}
	private List<Token> buildTokens(){
		char c;//current
		char p = '\0';//previus

		while(reader.haveNext()){
			while(Token.isBlank(c = reader.next())){}
			if (Token.isSub(c) && Token.isNumeric(reader.findNextNoBlank()) && (p == '\0' || Token.isLeftParenthesis(p) || Token.isComma(p))){
				reader.push();
			} else if (Token.isNumeric(c)){
				reader.push();
				while (reader.haveNext() && (Token.isNumeric(c = reader.next()) || Token.isDot(c))){
					reader.push();					
				}
				if (!(Token.isNumeric(c) || Token.isDot(c))){
					reader.prev();
				}
				tokens.add(new TokenNumber(reader.getBuffer()));
			} else if (Token.isAlpha(c)){
				reader.push();
				while (reader.haveNext() && Token.isAlphaNumeric(c = reader.next())){
					reader.push();					
				}
				if (!Token.isAlphaNumeric(c)){
					reader.prev();
				}
				tokens.add(new TokenAlias(reader.getBuffer()));
			} else if (Token.isApostrophe(c)){
				while (reader.haveNext()){
					p = c;
					c = reader.next();
					if (Token.isApostrophe(c)) {
						if (p != '\\') {
							break;			
						} else {
							reader.push();
						}
					} else {
						reader.push();
					}
				}
				if (!Token.isApostrophe(c)){
					throw new ParserFormulaException("Sintaxis invalida, se esperaba apostrofe \"'\" despues de: '" + reader.getBuffer() + "'");
				}
				tokens.add(new TokenString(reader.getBuffer()));
			} else if (Token.isComma(c)){
				tokens.add(TokenGroup.COMMA);
			} else if (Token.isDot(c)){
				tokens.add(TokenGroup.DOT);
			} else if (Token.isLeftParenthesis(c)){
				tokens.add(TokenGroup.L_PARENTHESIS);
			} else if (Token.isRightParenthesis(c)){
				tokens.add(TokenGroup.R_PARENTHESIS);
			} else if (TokenOperator.isSimpleOperator(c)){
				tokens.add(TokenOperator.getOperator(c));
			} else if (TokenOperator.isCompoundOperator(c)){
				reader.push();
				while (reader.haveNext() && TokenOperator.isCompoundOperator(c = reader.next())){
					reader.push();					
				}
				if (!TokenOperator.isCompoundOperator(c)){
					reader.prev();
				}
				String name = reader.getBuffer();
				TokenOperator op = TokenOperator.getOperator(name);
				if (op == null){
					throw new ParserFormulaException("Sintaxis operador invalido: '" + name + "'");
				}
				tokens.add(op);			
			} else {
				throw new ParserFormulaException("Token invalido: '" + c + "'");
			}
			p = reader.curr();
		}
		return tokens;
	}
	private Expression buildTree() {
		index = 0;
		sizeTokens = tokens.size();
		currExpression = null;
		prevExpressions.clear();
		root = parseExpression(sizeTokens);
		return root;
	}

	public Expression parseExpression(int end){
		if(index >= end){
			return null;
		}
		Token token = tokens.get(index);
		if (token == TokenGroup.L_PARENTHESIS){
			currExpression = new GROUP_NUMERIC().parse(token, this, findEndGroup(end));
		} else if (token instanceof TokenOperator){
			currExpression = Operator.parseOperator(token, this, end);
		} else if (token instanceof TokenString){
			currExpression = TypeString.parseString(token, this, end);
		} else if (token instanceof TokenNumber){
			currExpression =  TypeNumber.parseNumber(token, this, end);
		} else if (token instanceof TokenAlias){
			if (Alias.isAlias(token.getValue())){
				currExpression = Alias.parseAlias(token, this, end);
			} else if (Function.isFunction(token.getValue())){
				currExpression = Function.parseFunction(token, this, end);
			} else if (TypeBoolean.isBoolean(token.getValue())){
				currExpression = TypeBoolean.parseBoolean(token, this, end);
			} else {
				throw new ParserFormulaException("No se reconoce '" + token + "' como variable o funcion");
			}
		} else {
			throw new ParserFormulaException("Sintaxis invalida, token inesperado: '" + token+"'");
		}
		if (index < end){
			prevExpressions.push(currExpression);
			currExpression = parseExpression(end);
		}
		return currExpression;
	}
	public Expression[] parseArgs(int end){
		if (nextToken() != TokenGroup.L_PARENTHESIS){
			throw new ParserFormulaException("Sintaxis invalida es esperaban argumentos '"+TokenGroup.L_PARENTHESIS+"'");
		}
		List<Expression> expressions = new ArrayList<Expression>();
		int i = index++, open = 0;
		end = findEndGroup(end);
		while(++i < end){
			Token t = tokens.get(i);
			if (t == TokenGroup.L_PARENTHESIS){
				open++;
			} else if (t == TokenGroup.R_PARENTHESIS){
				open--;
			} else if (t == TokenGroup.COMMA && open == 0){
				expressions.add(parseExpression(i));
				index++;
			}
		}
		expressions.add(parseExpression(end));
		index++;
		return expressions.toArray(new Expression[expressions.size()]);
	}
	public int findEndGroup(int end){
		int i = index, open = 1;
		while(open > 0 && ++i < end){
			Token t = tokens.get(i);
			if (t == TokenGroup.L_PARENTHESIS){
				open++;
			} else if (t == TokenGroup.R_PARENTHESIS){
				open--;
			}
		}
		if (open > 0){
			throw new ParserFormulaException("Sintaxis invalida se esperaba: '"+TokenGroup.R_PARENTHESIS+"'");	
		}
		return i;
	}
	public boolean haveNextToken(){
		return index + 1 < sizeTokens;
	}
	public Token nextToken(){
		if (++index>= sizeTokens){
			return null;
		}
		return tokens.get(index);
	}
	public Token currToken(){
		return tokens.get(index);
	}
	public Token prevToken(){
		if (index - 1 < 0){
			index = -1;
			return null;
		}
		return tokens.get(--index);
	}
	public java.util.Stack<Expression> getPrevExpressions() {
		return prevExpressions;
	}
	public void setPrevExpressions(java.util.Stack<Expression> prevExpressions) {
		this.prevExpressions = prevExpressions;
	}
	public boolean havePrevExpression(){
		return !prevExpressions.isEmpty();
	}
	public Expression getPrevExpression(){
		return prevExpressions.pop();
	}
	public Message getMsg() {
		return msg;
	}
	public void setMsg(Message msg) {
		this.msg = msg;
	}
	@Override
	public String toString() {
		return reader.toString(); 
	}
}
