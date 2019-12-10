package com.fimet.commons.numericparser;
/**
 * 
 * @author <a href="mailto:marcoasb99@ciencias.unam.mx">Marco A. Salazar</a>
 *
 */
public interface INumericParser {
	public int getId();
	public int parse(byte[] bytes);
	public byte[] format(int number, int length);
}
