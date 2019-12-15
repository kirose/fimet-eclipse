package com.fimet.core.ISO8583.field.validator;

import java.util.HashMap;
import java.util.Map;

import com.fimet.commons.exception.FieldValidatorException;
import com.fimet.core.ISO8583.field.validator.impl.*;

public class FieldValidatorManager implements IFieldValidatorManager {
	private Map<String, IFieldValidator> cache = new HashMap<>();
	@Override
	public IFieldValidator getFieldValidator(String className) {
		if (cache.containsKey(className)) {
			return cache.get(className);
		} else {
			try {
				IFieldValidator validator = (IFieldValidator)Class.forName(className).newInstance();
				cache.put(className, validator);
				return validator;
			} catch (InstantiationException|IllegalAccessException|ClassNotFoundException e) {
				throw new FieldValidatorException("Cannot instantiate Validator: " + className, e);
			}
		}
	}
	@Override
	public void free() {}
	@Override
	public void saveState() {}
	//static final Class<?>[] classesValidators = new Class<?>[] {None.class,Update_hh.class,Update_hhmmss.class,Update_mm.class,Update_MMdd.class,Update_MMddhhmmss.class,Update_ss.class,UpdateMM.class,UpdatePanLastDigit.class};
	public Class<?>[] getClassesValidator(){
		return new Class<?>[] {
			None.class,
			Update_hh.class,
			Update_hhmmss.class,
			Update_mm.class,
			Update_MMdd.class,
			Update_MMddhhmmss.class,
			Update_ss.class,
			UpdateMM.class,
			UpdatePanLastDigit.class
		};
	}
}
