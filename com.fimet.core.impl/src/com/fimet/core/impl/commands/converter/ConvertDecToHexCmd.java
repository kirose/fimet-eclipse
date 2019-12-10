package com.fimet.core.impl.commands.converter;

import com.fimet.core.impl.utils.ConverterUtils;

public class ConvertDecToHexCmd extends ConvertAbstractCmd{
	protected String convert(String hex) {
		return ConverterUtils.integerToHex(hex);
	}
}