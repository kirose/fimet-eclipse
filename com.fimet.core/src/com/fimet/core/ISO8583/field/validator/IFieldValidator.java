package com.fimet.core.ISO8583.field.validator;

import com.fimet.core.ISO8583.parser.IMessage;

public interface IFieldValidator {
	void validate(String idField, IMessage message);
}
