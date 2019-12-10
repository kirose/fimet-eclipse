package com.fimet.commons.converter;

/**
 * 
 * @author <a href="mailto:marcoasb99@ciencias.unam.mx">Marco A. Salazar</a>
 *
 */
public class ConverterAsciiToHex extends Converter {
	ConverterAsciiToHex(int id, String name) {
		super(id, name);
	}

	@Override
	public byte[] convert(byte[] ascii) {
		return asciiToHex(ascii);
	}

	@Override
	public byte[] deconvert(byte[] hex) {
		return hexToAscii(hex);
	}
}
