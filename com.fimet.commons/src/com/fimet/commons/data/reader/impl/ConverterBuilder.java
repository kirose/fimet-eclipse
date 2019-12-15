package com.fimet.commons.data.reader.impl;

import com.fimet.commons.converter.Converter;
import com.fimet.commons.data.reader.IConverterBuilder;
import com.fimet.commons.exception.ConverterException;

/**
 * This class contains useful methods to convert the chunk into some representation:
 * -hexadecimal
 * -binary
 * -ascci
 * -ebcdic
 * -int
 * 
 * @author <a href="mailto:marcoasb99@ciencias.unam.mx">Marco A. Salazar</a>
 *
 */
public class ConverterBuilder implements IConverterBuilder {

	public static final short TYPE_ASCII = 0;
	public static final short TYPE_EBCDIC = 1;
	public static final short TYPE_HEX = 2;
	static final short TYPE_BINARY = 3;
	static final short TYPE_INT = 4;
	
	private Object value;
	private short type;
	public ConverterBuilder(byte[] bytes) {
		this(bytes, TYPE_ASCII);
	}
	public ConverterBuilder(byte[] bytes, short type) {
		if (type != TYPE_ASCII && type != TYPE_EBCDIC && type != TYPE_HEX) {
			throw new ConverterException("Invalid Conversion");
		}
		value = bytes;
		this.type = type;
	}
	private void hexToBinary() {
		value = Converter.hexToBinary(getBytesValue());
		type = TYPE_BINARY;
	}
	private void hexToAscii() {
		value = Converter.hexToAscii(getBytesValue());
		type = TYPE_ASCII;
	}
	private void hexToInt(int radix) {
		value = Integer.parseInt(getStringValue(),radix);
		type = TYPE_INT;
	}
	private void hexToEbcdic() {
		value = Converter.hexToEbcdic(getBytesValue());
		type = TYPE_EBCDIC;
	}
	private void asciiToBinary() {
		value = Converter.asciiToBinary(getBytesValue());
		type = TYPE_BINARY;
	}
	private void asciiToHex() {
		value = Converter.asciiToHex(getBytesValue());
		type = TYPE_HEX;
	}
	private void asciiToEbcdic() {
		value = Converter.asciiToEbcdic(getBytesValue());
		type = TYPE_EBCDIC;
	}
	private void asciiToInt(int radix) {
		value = Integer.parseInt(toString(), radix);
		type = TYPE_INT;
	}
	private void ebcdicToBinary() {
		value = Converter.ebcdicToBinary(getBytesValue());
		type = TYPE_BINARY;
	}
	private void ebcdicToHex() {
		value = Converter.ebcdicToHex(getBytesValue());
		type = TYPE_HEX;
	}
	private void ebcdicToAscii() {
		value = Converter.ebcdicToAscii(getBytesValue());
		type = TYPE_ASCII;
	}
	private void ebcdicToInt(int radix) {
		value = Integer.parseInt(toString(), radix);
		type = TYPE_INT;
	}
	private void binaryToEbcdic() {
		value = Converter.binaryToEbcdic(getBytesValue());
		type = TYPE_EBCDIC;
	}
	private void binaryToHex() {
		value = Converter.binaryToHex(getBytesValue());
		type = TYPE_HEX;
	}
	private void binaryToAscii() {
		value = Converter.binaryToAscii(getBytesValue());
		type = TYPE_ASCII;
	}
	private void binaryToInt() {
		value = Integer.parseInt(getStringValue(),2);
		type = TYPE_INT;
	}
	public int toInt() {
		return toInt(10);
	}
	public int toInt(int radix) {
		switch (type) {
			case TYPE_BINARY:binaryToInt();break;
			case TYPE_HEX:hexToInt(radix); break;
			case TYPE_ASCII:asciiToInt(radix); break;
			case TYPE_EBCDIC:ebcdicToInt(radix); break;
			case TYPE_INT: break;
			default: throw new ConverterException("Invalid conversion");
		}
		return getIntValue();
	}
	public IConverterBuilder toHex() {
		switch (type) {
			case TYPE_BINARY:binaryToHex(); break;
			case TYPE_ASCII:asciiToHex(); break;
			case TYPE_EBCDIC:ebcdicToHex(); break;
			case TYPE_HEX: break;
			default: throw new ConverterException("Invalid conversion");
		}
		return this;
	}
	public IConverterBuilder toBinary() {
		switch (type) {
			case TYPE_HEX:hexToBinary(); break;
			case TYPE_ASCII:asciiToBinary(); break;
			case TYPE_EBCDIC:ebcdicToBinary(); break;
			case TYPE_BINARY: break;
			default: throw new ConverterException("Invalid conversion");
		}
		return this;
	}
	public IConverterBuilder toAscii() {
		switch (type) {
			case TYPE_HEX:hexToAscii(); break;
			case TYPE_BINARY:binaryToAscii(); break;
			case TYPE_EBCDIC:ebcdicToAscii(); break;
			case TYPE_ASCII: break;
			default: throw new ConverterException("Invalid conversion");
		}
		return this;
	}
	public IConverterBuilder toEbcdic() {
		switch (type) {
		case TYPE_HEX:hexToEbcdic(); break;
		case TYPE_BINARY:binaryToEbcdic(); break;
		case TYPE_EBCDIC: break;
		case TYPE_ASCII:asciiToEbcdic(); break;
		default: throw new ConverterException("Invalid conversion");
	}
	return this;
	}
	public IConverterBuilder asBinary() {
		if (type == TYPE_ASCII || type == TYPE_EBCDIC) {
			toString();
		}
		type = TYPE_BINARY;
		return this;
	}
	public IConverterBuilder asHex() {
		type = TYPE_HEX;
		return this;
	}
	public IConverterBuilder asAscii() {
		type = TYPE_ASCII;
		return this;
	}
	public IConverterBuilder asEbcdic() {
		type = TYPE_EBCDIC;
		return this;
	}
	public Integer getIntValue() {
		return (Integer) value;
	}
	private String getStringValue() {
		if (value instanceof String) {
			return ((String)value);
		}
		if (value instanceof byte[]) {
			return new String((byte[]) value);
		}
		return value != null ? value.toString() : null;
	}
	private byte[] getBytesValue() {
		return toBytes();
	}
	public byte[] toBytes() {
		if (value instanceof byte[]) {
			return (byte[]) value;
		}
		if (value instanceof String) {
			return ((String)value).getBytes();
		}
		return value != null ? value.toString().getBytes() : null;
	}
	public String toString() {
		if (value instanceof String) {
			return (String)value;
		} else {
			switch (type) {
			case TYPE_EBCDIC: byteArrayToString();break;
			case TYPE_ASCII: byteArrayToString(); break;
			case TYPE_INT: intToString(); break;
			}
		}
		if (value instanceof byte[]) {
			return (String)(value = new String((byte[])value));
		} else if (value instanceof String) {
			return (String) value;
		} else {
			throw new ConverterException("Invalid conversion");
		}
	}

	private void intToString() {
		value = ""+value;
	}

	private void byteArrayToString() {
		StringBuilder sb = new StringBuilder();
		byte[] bytes = getBytesValue();
		for (int i = 0; i < bytes.length; i++) {
			sb.append((char)bytes[i]);
		}
		value = sb.toString();
	}
	
}
