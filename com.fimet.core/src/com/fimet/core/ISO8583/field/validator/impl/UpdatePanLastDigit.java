package com.fimet.core.ISO8583.field.validator.impl;


import com.fimet.commons.utils.PanUtils;
import com.fimet.core.ISO8583.field.validator.IFieldValidator;
import com.fimet.core.ISO8583.parser.IMessage;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class UpdatePanLastDigit implements IFieldValidator {
	private static UpdatePanLastDigit instance;
	public static UpdatePanLastDigit getInstance() {
		if (instance == null) {
			instance = new UpdatePanLastDigit();
		}
		return instance;
	}
	public void validate(String idField, IMessage message) {
		if (!message.hasField(idField)) {
			return;
		}
		String track2 = message.getValue(idField);
		if (
			track2 != null && track2.length() >= 16 && 
			track2.substring(0,16).matches("[0-9]{16}") && 
			(track2.length() == 16 || track2.indexOf('D') == 16 || track2.indexOf('=') == 16)
		){
			String pan = track2.substring(0,16);
			char lastDigit = PanUtils.calculateLastDigit(pan);
			String value = pan.substring(0,15)+lastDigit+track2.substring(16);
			message.setField(idField, value.getBytes());
		}
	}
}
