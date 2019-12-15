package com.fimet.core.impl.commands.converter;

import com.fimet.core.impl.utils.ConverterUtils;

public class ConvertEbcdicToHexCmd extends ConvertAbstractCmd{
	protected String convert(String ebcdic) {
		return ConverterUtils.ebcdicToHex(ebcdic);
	}
}