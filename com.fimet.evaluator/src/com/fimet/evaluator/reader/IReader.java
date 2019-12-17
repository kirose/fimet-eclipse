package com.fimet.evaluator.reader;

public interface IReader {
	public boolean hasNext();
	public char next();
	public char prev();
	public char curr();
	public char nextNotBlank();
	public char nextNumeric();
	public char nextAlpha();
	public char nextAlphaNumeric();
	
	public char peekNext();
	public char peekPrev();
	public char peekNextNotBlank();
	
	public void packPrev();
	public void packCurr();
	public void packNext();
	public String getPack();
	public String packNumeric();
	public String packAlpha();
	public String packSeparator();
	public String packGroup();
	public String packAlphaNumeric();
	public String packOperator();
	public String packString();

	public boolean isAlpha();
	public boolean isAlphaNumeric();
	public boolean isNumeric();
	public boolean isAdd();
	public boolean isSub();
	public boolean isMul();
	public boolean isDiv();
	public boolean isPow();
	public boolean isMod();
	public boolean isDot();
	public boolean isComma();
	public boolean isLeftBracket();
	public boolean isRightBracket();
	public boolean isLeftSquareBracket();
	public boolean isRightSquartBracket();
	public boolean isLeftParenthesis();
	public boolean isRightParenthesis();
	public boolean isApostrophe();
	public boolean isDoubleApostrophe();
	public boolean isSingleApostrophe();
	public boolean isOperator();
	public boolean isGroup();
	public boolean isSeparator();
}

