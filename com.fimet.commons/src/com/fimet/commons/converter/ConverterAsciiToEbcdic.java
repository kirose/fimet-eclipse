package com.fimet.commons.converter;

/**
 * 
 * @author <a href="mailto:marcoasb99@ciencias.unam.mx">Marco A. Salazar</a>
 *
 */
public class ConverterAsciiToEbcdic extends Converter {
	ConverterAsciiToEbcdic(int id, String name) {
		super(id, name);
	}

	@Override
	public byte[] convert(byte[] ascii) {
		return asciiToEbcdic(ascii);
	}

	@Override
	public byte[] deconvert(byte[] ebcdic) {
		return ebcdicToAscii(ebcdic);
	}
}
