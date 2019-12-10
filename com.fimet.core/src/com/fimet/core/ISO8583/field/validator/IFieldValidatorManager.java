package com.fimet.core.ISO8583.field.validator;

import com.fimet.core.IManager;

public interface IFieldValidatorManager extends IManager {
	IFieldValidator getFieldValidator(String className);
	public Class<?>[] getClassesValidator();
}
