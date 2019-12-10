package com.fimet.commons.converter;

/**
 * 
 * @author <a href="mailto:marcoasb99@ciencias.unam.mx">Marco A. Salazar</a>
 *
 */
public class ConverterHexToEbcdic extends Converter {
	ConverterHexToEbcdic(int id, String name) {
		super(id, name);
	}

	@Override
	public byte[] convert(byte[] hex) {
		return hexToEbcdic(hex);
	}

	@Override
	public byte[] deconvert(byte[] ebcdic) {
		return ebcdicToHex(ebcdic);
	}
}
