package com.fimet.core.impl.popup.action.converter;

import com.fimet.core.impl.utils.ConverterUtils;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class ConvertAsciiToHexAction extends ConvertAbstractAction {
	protected String convert(String ascii) {
		return ConverterUtils.asciiToHex(ascii);
	}
}
