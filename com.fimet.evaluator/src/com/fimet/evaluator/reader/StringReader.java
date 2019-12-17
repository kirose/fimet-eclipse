package com.fimet.evaluator.reader;




public class StringReader extends AbstractReader {

	private final char[] source;
	private final int length;
	private int index;

	public StringReader(String source) {
		super();
		this.source = source.toCharArray();
		this.index = 0;
		this.length = source.length();
	}
	public boolean hasNext() {
		return index < length;
	}
	public char next() {
		return source[index++];
	}
	public char prev() {
		return source[--index];
	}
	public char curr() {
		if (index == 0) {
			return '\0';
		} else {
			return source[index-1];
		}
	}
	public char nextNotBlank() {
		while(hasNext() && !isBlank(peekNext())) {index++;}
		if (index > length) {
			return '\0';
		}
		return next();
	}
	public char nextNumeric() {
		while(hasNext() && !isNumeric(peekNext())) {index++;}
		if (index > length) {
			return '\0';
		}
		return next();
	}
	public char nextAlpha() {
		while(hasNext() && !isAlpha(peekNext())) {index++;}
		if (index > length) {
			return '\0';
		}
		return next();
	}
	public char nextAlphaNumeric() {
		while(hasNext() && !isAlphaNumeric(peekNext())) {index++;}
		if (index > length) {
			return '\0';
		}
		return next();
	}
	
	public char peekPrev() {
		if (index < 2) {
			return '\0';
		} else {
			return source[index-2];
		}
	}	
	public char peekNext() {
		if (hasNext()) {
			return source[index];
		} else {
			return '\0';
		}
	}
	public char peekNextNotBlank() {
		int peek = index;
		while(peek < length && !isBlank(source[peek])) {peek++;}
		if (peek > length) {
			return '\0';
		} else {
			return source[peek];
		}
	}
}
