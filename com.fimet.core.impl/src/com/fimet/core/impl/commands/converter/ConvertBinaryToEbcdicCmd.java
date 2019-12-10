package com.fimet.core.impl.commands.converter;

import com.fimet.core.impl.utils.ConverterUtils;

public class ConvertBinaryToEbcdicCmd extends ConvertAbstractCmd{
	protected String convert(String bits) {
		return ConverterUtils.binaryToEbcdic(bits);
	}
}