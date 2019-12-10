package com.fimet.core.stress;

public interface IStressReader {

	void open();
	boolean hasNext();
	byte[] next();
	void close();

}
