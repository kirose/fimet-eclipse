package com.fimet.evaluator.token;

/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class Token {
	String value;
	public Token(String token){
		this.value = token;
	}
	public Token(char token){
		this.value = ""+token;
	}
	@Override
	public String toString() {
		return value;
	}
	public String getValue(){
		return value;
	}
}
