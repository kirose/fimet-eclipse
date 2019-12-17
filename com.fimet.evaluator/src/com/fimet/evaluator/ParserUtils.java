package com.fimet.evaluator;

import java.lang.reflect.Array;

import com.fimet.evaluator.type.Expression;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public final class ParserUtils {
	private ParserUtils() {}
	public static <T> T[] add(T[] items, T item) {
		@SuppressWarnings("unchecked")
		T[] temp = (T[])Array.newInstance(item.getClass(), items.length+1);
		System.arraycopy(items, 0, temp, 0, items.length);
		temp[items.length] = item;
		return temp;
	}
	public static String implode(Expression[] arr, String separator){
		if (arr == null || arr.length == 0){
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (Expression a : arr) {
			sb.append(a.toString()).append(separator);
		}
		sb.delete(sb.length()-separator.length(), sb.length());
		return sb.toString();
	}
	public static String implode(Object[] arr, String separator){
		if (arr == null || arr.length == 0){
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (Object a : arr) {
			sb.append(a.toString()).append(separator);
		}
		sb.delete(sb.length()-separator.length(), sb.length());
		return sb.toString();
	}
	public static boolean isAdd(char c){
		return c == '+';
	}
	public static boolean isSub(char c){
		return c == '-';
	}
	public static boolean isMul(char c){
		return c == '*';
	}
	public static boolean isDiv(char c){
		return c == '/';
	}
	public static boolean isPow(char c){
		return c == '^';
	}
	public static boolean isMod(char c){
		return c == '%';
	}
	public static boolean isDot(char c){
		return c == '.';
	}
	public static boolean isComma(char c){
		return c == ',';
	}
	public static boolean isLeftParenthesis(char c){
		return c == '(';
	}
	public static boolean isRightParenthesis(char c){
		return c == ')';
	}
	public static boolean isApostrophe(char c){
		return isSingleApostrophe(c) || isDoubleApostrophe(c);
	}
	public static boolean isOperator(char c){
		return c == '+' || c == '-' || c == '*' || c == '/' ||
			   c == '^' || c == '%' || c == '<' || c == '>' ||
			   c == '=' || c == '!' || c == '&' || c == '|';
	}
	public static boolean isSingleApostrophe(char c){
		return c == '\'';
	}
	public static boolean isDoubleApostrophe(char c){
		return c == '"';
	}
	public static boolean isBlank(char c){
		return c == ' ' || c == '\n' || c == '\t' || c == '\r';
	}
	public static boolean isAlpha(char c){
		return isAlphaLower(c) || isAlphaUpper(c);
	}
	public static boolean isAlphaNumeric(char c){
		return isAlpha(c) || isNumeric(c);
	}
	public static boolean isAlphaLower(char c){
		return 97 <= c && c <= 122;
	}
	public static boolean isAlphaUpper(char c){
		return 65 <= c && c <= 90;
	}
	public static boolean isNumeric(char c){
		return 48 <= c && c <= 57;
	}
}
