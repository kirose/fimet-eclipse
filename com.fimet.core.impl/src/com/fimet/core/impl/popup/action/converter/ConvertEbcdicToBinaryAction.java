package com.fimet.core.impl.popup.action.converter;

import com.fimet.core.impl.utils.ConverterUtils;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class ConvertEbcdicToBinaryAction extends ConvertAbstractAction {
	protected String convert(String ebcdic) {
		return ConverterUtils.ebcdicToBinary(ebcdic);
	}
}