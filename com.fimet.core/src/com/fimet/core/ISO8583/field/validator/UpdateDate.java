package com.fimet.core.ISO8583.field.validator;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fimet.core.ISO8583.parser.IMessage;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public abstract class UpdateDate implements IFieldValidator {

	protected static SimpleDateFormat FMT_MMddhhmmss = new SimpleDateFormat("MMddkkmmss");
	protected static SimpleDateFormat FMT_hhmmss = new SimpleDateFormat("kkmmss");
	protected static SimpleDateFormat FMT_MMdd = new SimpleDateFormat("MMdd");
	protected static SimpleDateFormat FMT_MM = new SimpleDateFormat("MM");
	protected static SimpleDateFormat FMT_dd = new SimpleDateFormat("dd");
	protected static SimpleDateFormat FMT_hh = new SimpleDateFormat("kk");
	protected static SimpleDateFormat FMT_mm = new SimpleDateFormat("mm");
	protected static SimpleDateFormat FMT_ss = new SimpleDateFormat("ss");

	abstract protected String format(Date date);
	public void validate(String idField, IMessage message) {
		if (!message.hasField(idField)) {
			message.setField(idField, format(new Date()).getBytes());
		}
	}
}
