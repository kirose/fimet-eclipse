package com.fimet.core.ISO8583.field.validator.impl;

import com.fimet.core.ISO8583.field.validator.IFieldValidator;
import com.fimet.core.ISO8583.parser.IMessage;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class None implements IFieldValidator {
	@Override
	public void validate(String idField, IMessage message) {}
	
}
