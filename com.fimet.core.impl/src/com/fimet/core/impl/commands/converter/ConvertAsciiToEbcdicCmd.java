package com.fimet.core.impl.commands.converter;

import com.fimet.core.impl.utils.ConverterUtils;

public class ConvertAsciiToEbcdicCmd extends ConvertAbstractCmd{
	protected String convert(String ascii) {
		return ConverterUtils.asciiToEbcdic(ascii);
	}
}