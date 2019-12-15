package com.fimet.core.ISO8583.parser;

import java.util.ArrayList;
import java.util.List;

import com.fimet.commons.data.reader.IReader;
import com.fimet.commons.data.reader.impl.ByteArrayReader;
import com.fimet.core.Activator;
import com.fimet.core.IFieldParserManager;
import com.fimet.core.entity.sqlite.Parser;
/**
 * Una clase abstracta que genera un parseo general de un mensaje ISO-8583 a un Message
 * Unicamente se parsean campos presentes en el Bitmap
 *    
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public abstract class AbstractMessageParser extends AbstractMessageFormater {
	public AbstractMessageParser(Parser entity) {
		super(entity);
	}
	@Override
	public Message parseMessage(byte[] bytes) {
		Message msg = new Message();
		msg.setParser(this);
		IReader reader = new ByteArrayReader(getConverter().convert(bytes));
		parseHeader(reader, msg);
		int[] bitmap = parseBitmap(reader);
		parseFields(reader, bitmap, msg);
		return msg;
	}
	protected void parseHeader(IReader reader, Message msg) {
		String headerValue = reader.ascii(16).toString();// ISO0234000700200
		msg.setHeader(headerValue.substring(0, 12));
		msg.setMti(headerValue.substring(12, 16));
	}
	protected int[] parseBitmap(IReader reader) {
		List<Integer> bitmap = new ArrayList<>();
		String bm;
		int noField = 2;
		do {
			bm = reader.hex(16).toBinary().toString();
			for (int i = 1; i < bm.length(); i++) {
				if (bm.charAt(i) == '1') {
					bitmap.add(noField);
				}
				noField++;
			}
			noField++;
		} while (bm.charAt(0) == '1');
		int[] bitmapArray = new int[bitmap.size()];
		int i = 0;
		for (Integer id : bitmap) {
			bitmapArray[i++] = id;
		}
		return bitmapArray;
	}
	protected void parseFields(IReader reader, int[] bitmap, Message message) {
		Integer index = null;
		try {
			IFieldParserManager manager = getFieldParserManager();
			for (int id : bitmap) {
				manager.parse(id, reader, message);
			}
		} catch (Exception e) {
			if (getFailOnError()) {
				throw e;
			} else {
				Activator.getInstance().warning(this+" error parsing field "+index,e);
			}
		}
	}
	private boolean getFailOnError() {
		return false;//PreferenceDAO.getBoolean(PreferenceDAO.FAIL_ON_PARSE_FIELD_ERROR, Parser.DEFAUT_FAIL_ON_ERROR);
	}
}
