package com.fimet.commons.converter;

/**
 * 
 * @author <a href="mailto:marcoasb99@ciencias.unam.mx">Marco A. Salazar</a>
 *
 */
public class ConverterBinaryToAscii extends Converter {
	ConverterBinaryToAscii(int id, String name) {
		super(id, name);
	}

	@Override
	public byte[] convert(byte[] binary) {
		return binaryToAscii(binary);
	}

	@Override
	public byte[] deconvert(byte[] ascii) {
		return asciiToBinary(ascii);
	}
}
