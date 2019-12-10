package com.fimet.commons.data.reader;
/**
 * 
 * @author Marco Antonio
 *
 */
public interface IReader {
	IConverterBuilder ascii(int length);
	IConverterBuilder ebcdic(int length);
	IConverterBuilder hex(int length);
	IMatcher matcher(String match);
	int length();
	int current();
	void move(int no);
	void assertChar(char c);
	boolean hasNext();
	byte[] getBytes(int length);
	byte[] read(int length);
}
