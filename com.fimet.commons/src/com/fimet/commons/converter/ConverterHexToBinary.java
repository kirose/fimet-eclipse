package com.fimet.commons.converter;

/**
 * 
 * @author <a href="mailto:marcoasb99@ciencias.unam.mx">Marco A. Salazar</a>
 *
 */
public class ConverterHexToBinary extends Converter {
	ConverterHexToBinary(int id, String name) {
		super(id, name);
	}

	@Override
	public byte[] convert(byte[] hex) {
		return hexToBinary(hex);
	}

	@Override
	public byte[] deconvert(byte[] binary) {
		return binaryToHex(binary);
	}
}
