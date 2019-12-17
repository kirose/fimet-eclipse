package com.fimet.evaluator;



import com.fimet.commons.exception.ParserFormulaException;
import com.fimet.commons.utils.StringUtils;
import com.fimet.evaluator.token.Token;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class ParserReader {
	String src;
	StringBuffer buffer;
	int length;
	int index = -1;
	char curr;
	
	public ParserReader(String formula) {
		if (StringUtils.isBlank(formula)){
			throw new ParserFormulaException("La formula es nula");
		}
		this.src = formula;
		this.length = formula.length();
		this.buffer = new StringBuffer();
	}
	public char prev(){
		return curr = src.charAt(--index);
	}
	public char curr(){
		return curr;
	}
	public char findNextNoBlank(){
		int i = index;
		char c = '\0';
		while(Token.isBlank(c = src.charAt(++i)) && i < length){}
		return c;
	}
	public char next(){
		return curr = src.charAt(++index); 
	}
	public boolean haveNext(){
		return index + 1 < length;
	}
	public ParserReader push(){
		buffer.append(curr);
		return this;
	}
	public String getBuffer(){
		String s = buffer.toString();
		buffer = new StringBuffer();
		return s;
	}
	public ParserReader reset(){
		buffer = new StringBuffer();
		curr = '\0';
		return this;
	}
	public String toString(){
		return src;
	}
}
