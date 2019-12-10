package com.fimet.commons.converter;
/**
 * 
 * @author <a href="mailto:marcoasb99@ciencias.unam.mx">Marco A. Salazar</a>
 *
 */
public class ConverterHexToAscii extends Converter {
	ConverterHexToAscii(int id, String name) {
		super(id, name);
	}

	@Override
	public byte[] convert(byte[] hex) {
		return hexToAscii(hex);
	}

	@Override
	public byte[] deconvert(byte[] ascii) {
		return asciiToHex(ascii);
	}
}
