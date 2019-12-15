package com.fimet.commons.converter;

/**
 * 
 * @author <a href="mailto:marcoasb99@ciencias.unam.mx">Marco A. Salazar</a>
 *
 */
public class ConverterEbcdicToAscii extends Converter {
	ConverterEbcdicToAscii(int id, String name) {
		super(id, name);
	}

	@Override
	public byte[] convert(byte[] ebcdic) {
		return ebcdicToAscii(ebcdic);
	}

	@Override
	public byte[] deconvert(byte[] ascii) {
		return asciiToEbcdic(ascii);
	}
}
