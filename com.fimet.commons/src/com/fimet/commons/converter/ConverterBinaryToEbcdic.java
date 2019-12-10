package com.fimet.commons.converter;

/**
 * 
 * @author <a href="mailto:marcoasb99@ciencias.unam.mx">Marco A. Salazar</a>
 *
 */
public class ConverterBinaryToEbcdic extends Converter {
	ConverterBinaryToEbcdic(int id, String name) {
		super(id, name);
	}

	@Override
	public byte[] convert(byte[] binary) {
		return binaryToAscii(binary);
	}

	@Override
	public byte[] deconvert(byte[] ebcdic) {
		return ebcdicToBinary(ebcdic);
	}
}
