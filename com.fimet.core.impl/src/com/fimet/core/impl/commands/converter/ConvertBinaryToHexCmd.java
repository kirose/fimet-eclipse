package com.fimet.core.impl.commands.converter;

import com.fimet.core.impl.utils.ConverterUtils;

public class ConvertBinaryToHexCmd extends ConvertAbstractCmd{
	protected String convert(String bits) {
		return ConverterUtils.binaryToHex(bits);
	}
}