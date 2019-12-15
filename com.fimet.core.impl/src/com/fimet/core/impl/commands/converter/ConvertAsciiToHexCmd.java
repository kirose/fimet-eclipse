package com.fimet.core.impl.commands.converter;

import com.fimet.core.impl.utils.ConverterUtils;

public class ConvertAsciiToHexCmd extends ConvertAbstractCmd{
	protected String convert(String ascii) {
		return ConverterUtils.asciiToHex(ascii);
	}
}