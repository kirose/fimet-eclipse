package com.fimet.core.impl.commands.converter;

import com.fimet.commons.utils.PanUtils;

public class UpdateLastDigitCmd extends ConvertAbstractCmd{
	protected String convert(String pan) {
		if (pan == null || pan.length() != 16) {
			return pan;
		} else {
			char last = PanUtils.calculateLastDigit(pan);
			return pan.substring(0,15)+last;
		}
	}
}