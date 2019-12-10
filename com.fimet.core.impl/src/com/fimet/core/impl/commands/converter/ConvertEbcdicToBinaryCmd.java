package com.fimet.core.impl.commands.converter;

import com.fimet.core.impl.utils.ConverterUtils;

public class ConvertEbcdicToBinaryCmd extends ConvertAbstractCmd{
	protected String convert(String ebcdic) {
		return ConverterUtils.ebcdicToBinary(ebcdic);
	}
}