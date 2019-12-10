package com.fimet.commons.converter;

/**
 * 
 * @author <a href="mailto:marcoasb99@ciencias.unam.mx">Marco A. Salazar</a>
 *
 */
public class ConverterEbcdicToBinary extends Converter {
	ConverterEbcdicToBinary(int id, String name) {
		super(id, name);
	}

	@Override
	public byte[] convert(byte[] ebcdic) {
		return ebcdicToBinary(ebcdic);
	}

	@Override
	public byte[] deconvert(byte[] binary) {
		return binaryToEbcdic(binary);
	}
}
