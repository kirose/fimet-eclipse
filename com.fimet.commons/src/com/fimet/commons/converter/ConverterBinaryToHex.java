package com.fimet.commons.converter;

/**
 * 
 * @author <a href="mailto:marcoasb99@ciencias.unam.mx">Marco A. Salazar</a>
 *
 */
public final class ConverterBinaryToHex extends Converter {
	ConverterBinaryToHex(int id, String name) {
		super(id, name);
	}

	@Override
	public byte[] convert(byte[] binary) {
		return binaryToHex(binary);
	}

	@Override
	public byte[] deconvert(byte[] hex) {
		return hexToBinary(hex);
	}
}
