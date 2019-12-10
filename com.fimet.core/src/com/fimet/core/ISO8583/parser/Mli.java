package com.fimet.core.ISO8583.parser;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class Mli {
	private int length;
	private byte[] bytes;
	private boolean exclusive;
	public Mli(int length, byte[] bytes, boolean exclusive) {
		this.length = length;
		this.bytes = bytes;
		this.exclusive = exclusive;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public byte[] getBytes() {
		return bytes;
	}
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	public boolean getExclusive() {
		return exclusive;
	}
	public void setExclusive(boolean exclusive) {
		this.exclusive = exclusive;
	}
}
