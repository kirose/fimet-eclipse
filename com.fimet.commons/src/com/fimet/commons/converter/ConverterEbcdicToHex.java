package com.fimet.commons.converter;

/**
 * 
 * @author <a href="mailto:marcoasb99@ciencias.unam.mx">Marco A. Salazar</a>
 *
 */
public class ConverterEbcdicToHex extends Converter {
	ConverterEbcdicToHex(int id, String name) {
		super(id, name);
	}

	@Override
	public byte[] convert(byte[] ebcdic) {
		return ebcdicToHex(ebcdic);
	}

	@Override
	public byte[] deconvert(byte[] hex) {
		return hexToEbcdic(hex);
	}
}
