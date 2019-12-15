package com.fimet.core.impl.popup.action.converter;

import com.fimet.commons.utils.PanUtils;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class UpdateLastDigitAction extends ConvertAbstractAction {
	protected String convert(String pan) {
		if (pan == null || pan.length() != 16) {
			return pan;
		} else {
			char last = PanUtils.calculateLastDigit(pan);
			return pan.substring(0,15)+last;
		}
	}
}