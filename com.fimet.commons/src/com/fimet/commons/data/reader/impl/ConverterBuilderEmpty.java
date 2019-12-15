package com.fimet.commons.data.reader.impl;


import com.fimet.commons.data.reader.IConverterBuilder;

/**
 * Represents a chunk of Message
 * This class contains useful methods to convert the chunk into some representation:
 * -hexadecimal
 * -bin
 * -ascci
 * 
 * @author Marco Antonio
 *
 */
public class ConverterBuilderEmpty implements IConverterBuilder {

	public ConverterBuilderEmpty() {}
	public int toInt() {
		return 0;
	}
	public int toInt(int radix) {
		return 0;
	}
	public IConverterBuilder toHex() {
		return this;
	}
	public IConverterBuilder toBinary() {
		return this;
	}
	public IConverterBuilder toAscii() {
		return this;
	}
	public IConverterBuilder toEbcdic() {
		return this;
	}
	public IConverterBuilder asBinary() {
		return this;
	}
	public IConverterBuilder asHex() {
		return this;
	}
	public IConverterBuilder asAscii() {
		return this;
	}
	public IConverterBuilder asEbcdic() {
		return this;
	}
	public Integer getIntValue() {
		return (Integer) 0;
	}
	public byte[] toBytes() {
		return new byte[0];
	}
	public String toString() {
		return "";
	}

}
