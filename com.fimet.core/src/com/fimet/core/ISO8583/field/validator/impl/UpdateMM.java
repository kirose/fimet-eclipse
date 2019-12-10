package com.fimet.core.ISO8583.field.validator.impl;

import java.util.Date;

import com.fimet.core.ISO8583.field.validator.UpdateDate;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class UpdateMM extends UpdateDate {

	@Override
	protected String format(Date date) {
		return FMT_MM.format(date);
	}
	
}
