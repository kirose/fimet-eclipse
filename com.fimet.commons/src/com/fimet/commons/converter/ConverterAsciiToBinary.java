package com.fimet.commons.converter;

/**
 * 
 * @author <a href="mailto:marcoasb99@ciencias.unam.mx">Marco A. Salazar</a>
 *
 */
public class ConverterAsciiToBinary extends Converter {
	
	ConverterAsciiToBinary(int id, String name) {
		super(id, name);
	}

	@Override
	public byte[] convert(byte[] ascii) {
		return asciiToBinary(ascii);
	}

	@Override
	public byte[] deconvert(byte[] binary) {
		return binaryToAscii(binary);
	}
}
