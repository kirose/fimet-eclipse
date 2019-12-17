package com.fimet.evaluator.reader;

import com.fimet.commons.exception.TokenException;

public abstract class AbstractReader implements IReader {
	
	protected StringBuilder packer = new StringBuilder();
	
	protected boolean isAdd(char c){
		return c == '+';
	}
	protected boolean isSub(char c){
		return c == '-';
	}
	protected boolean isMul(char c){
		return c == '*';
	}
	protected boolean isDiv(char c){
		return c == '/';
	}
	protected boolean isPow(char c){
		return c == '^';
	}
	protected boolean isMod(char c){
		return c == '%';
	}
	protected boolean isDot(char c){
		return c == '.';
	}
	protected boolean isComma(char c){
		return c == ',';
	}
	protected boolean isLeftBracket(char c){
		return c == '{';
	}
	protected boolean isRightBracket(char c){
		return c == '}';
	}
	protected boolean isLeftSquareBracket(char c){
		return c == '[';
	}
	protected boolean isRightSquareBracket(char c){
		return c == ']';
	}
	protected boolean isLeftParenthesis(char c){
		return c == '(';
	}
	protected boolean isRightParenthesis(char c){
		return c == ')';
	}
	protected boolean isApostrophe(char c){
		return isSingleApostrophe(c) || isDoubleApostrophe(c);
	}
	protected boolean isOperator(char c){
		return c == '+' || c == '-' || c == '*' || c == '/' ||
			   c == '^' || c == '%' || c == '<' || c == '>' ||
			   c == '=' || c == '!' || c == '&' || c == '|' || 
			   c == '?' || c == '.';
	}
	protected boolean isGroup(char c){
		return c == '{' || c == '}' || c == '(' || c == ')' ||
			   c == '[' || c == ']';
	}
	protected boolean isSeparator(char c){
		return c == ',' || c == ':' || c == ';';
	}
	protected boolean isSingleApostrophe(char c){
		return c == '\'';
	}
	protected boolean isDoubleApostrophe(char c){
		return c == '"';
	}
	protected boolean isBlank(char c){
		return c == ' ' || c == '\n' || c == '\t' || c == '\r';
	}
	protected boolean isAlpha(char c){
		return isAlphaLower(c) || isAlphaUpper(c);
	}
	protected boolean isAlphaNumeric(char c){
		return isAlpha(c) || isNumeric(c);
	}
	protected boolean isAlphaLower(char c){
		return 97 <= c && c <= 122;
	}
	protected boolean isAlphaUpper(char c){
		return 65 <= c && c <= 90;
	}
	protected boolean isNumeric(char c){
		return 48 <= c && c <= 57;
	}
	@Override
	public boolean isNumeric() {
		return isNumeric(curr());
	}
	@Override
	public boolean isAlpha() {
		return isAlpha(curr());
	}
	@Override
	public boolean isAlphaNumeric() {
		return isAlphaNumeric(curr());
	}
	@Override
	public boolean isAdd(){
		return isAdd(curr());
	}
	@Override
	public boolean isSub(){
		return isSub(curr());
	}
	@Override
	public boolean isMul(){
		return isMul(curr());
	}
	@Override
	public boolean isDiv(){
		return isDiv(curr());
	}
	@Override
	public boolean isPow(){
		return isPow(curr());
	}
	@Override
	public boolean isMod(){
		return isMod(curr());
	}
	@Override
	public boolean isDot(){
		return isDot(curr());
	}
	@Override
	public boolean isComma(){
		return isComma(curr());
	}
	@Override
	public boolean isLeftParenthesis(){
		return isLeftParenthesis(curr());
	}
	@Override
	public boolean isRightParenthesis(){
		return isRightParenthesis(curr());
	}
	@Override
	public boolean isApostrophe(){
		return isApostrophe(curr());
	}
	@Override
	public boolean isSingleApostrophe(){
		return isSingleApostrophe(curr());
	}
	@Override
	public boolean isDoubleApostrophe(){
		return isDoubleApostrophe(curr());
	}
	@Override
	public boolean isOperator() {
		return isOperator(curr());
	}
	@Override
	public boolean isGroup() {
		return isGroup(curr());
	}
	@Override
	public boolean isSeparator() {
		return isSeparator(curr());
	}
	@Override
	public boolean isLeftBracket() {
		return isLeftBracket(curr());
	}
	@Override
	public boolean isRightBracket() {
		return isRightBracket(curr());
	}
	@Override
	public boolean isLeftSquareBracket() {
		return isLeftSquareBracket(curr());
	}
	@Override
	public boolean isRightSquartBracket() {
		return isRightSquareBracket(curr());
	}
	public String getPack() {
		String pack = packer.toString();
		clearPacker();
		return pack;
	}
	public void packPrev() {
		packer.append(prev());
	}
	public void packCurr() {
		packer.append(curr());
	}
	public void packNext() {
		packer.append(next());
	}
	@Override
	public String packSeparator() {
		if (isSeparator(curr())) {
			packer.append(curr());
		}
		while(hasNext() && isSeparator(peekNext())) {
			packer.append(next());
		}
		String pack = packer.toString();
		clearPacker();
		return pack;
	}
	@Override
	public String packGroup() {
		if (isGroup(curr())) {
			packer.append(curr());
		}
		while(hasNext() && isGroup(peekNext())) {
			packer.append(next());
		}
		String pack = packer.toString();
		clearPacker();
		return pack;
	}
	public String packNumeric() {
		if (isNumeric(curr())) {
			packer.append(curr());
		}
		while(hasNext() && isNumeric(peekNext())) {
			packer.append(next());
		}
		if (hasNext() && isDot(peekNext())) {
			packer.append(peekNext());
		}
		while(hasNext() && (isNumeric(peekNext()))) {
			packer.append(next());
		}
		String pack = packer.toString();
		clearPacker();
		return pack;
	}
	public String packAlpha() {
		if (isAlpha(curr())) {
			packer.append(curr());
		}
		while(hasNext() && isAlpha(peekNext())) {packer.append(next());}
		String pack = packer.toString();
		clearPacker();
		return pack;
	}
	public String packAlphaNumeric() {
		if (isAlphaNumeric(curr())) {
			packer.append(curr());
		}
		while(hasNext() && isAlphaNumeric(peekNext())) {packer.append(next());}
		String pack = packer.toString();
		clearPacker();
		return pack;
	}
	@Override
	public String packString() {
		if (!hasNext()) {
			throw new TokenException("Unexpected char "+curr());	
		}
		if (isSingleApostrophe()) {
			next();
			while(hasNext() && isSingleApostrophe(peekNext()) && curr() != '\\') {
				packNext();
			}
			if (!hasNext()) {
				throw new TokenException("Expected single apostrophe at the end of string");	
			}
			next();
		} else if (isDoubleApostrophe()) {
			next();
			while(hasNext() && isDoubleApostrophe(peekNext()) && curr() != '\\') {
				packNext();
			}
			if (!hasNext()) {
				throw new TokenException("Expected doble apostrophe at the end of string");	
			}
			next();
		} else {
			throw new TokenException("Expected single or double apostrophe at the start of string");
		}
		return getPack();
	}
	public String packOperator() {
		if (isOperator(curr())) {
			packer.append(curr());
		}
		while(hasNext() && isOperator(peekNext())) {packer.append(next());}
		String pack = packer.toString();
		clearPacker();
		return pack;		
	}
	protected void clearPacker() {
		packer.delete(0, packer.length());
	}

}
