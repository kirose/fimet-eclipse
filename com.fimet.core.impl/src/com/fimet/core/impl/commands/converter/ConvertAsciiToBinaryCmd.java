package com.fimet.core.impl.commands.converter;

import com.fimet.core.impl.utils.ConverterUtils;

public class ConvertAsciiToBinaryCmd extends ConvertAbstractCmd{
	protected String convert(String ascii) {
		return ConverterUtils.asciiToBinary(ascii);
	}
}