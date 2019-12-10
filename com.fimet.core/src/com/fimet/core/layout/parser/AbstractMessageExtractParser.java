package com.fimet.core.layout.parser;

import java.util.List;

import com.fimet.commons.data.reader.IReader;
import com.fimet.commons.data.reader.impl.ByteArrayReader;
import com.fimet.core.Activator;
import com.fimet.core.ISO8583.parser.IFieldParser;
import com.fimet.core.ISO8583.parser.MessageExtract;
import com.fimet.core.entity.sqlite.Parser;
/**
 * Una clase abstracta que genera un parseo general de un mensaje ISO-8583 a un Message
 * Unicamente se parsean campos presentes en el Bitmap
 *    
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public abstract class AbstractMessageExtractParser extends AbstractMessageExtractFormater {
	public AbstractMessageExtractParser(Parser entity) {
		super(entity);
	}
	@Override
	public MessageExtract parseMessage(byte[] bytes) {
		MessageExtract msg = new MessageExtract();
		msg.setParser(this);
		IReader reader = new ByteArrayReader(getConverter().convert(bytes));
		parseFields(reader, msg);
		return msg;
	}
	protected void parseFields(IReader reader, MessageExtract message) {
		List<IFieldParser> roots = getFieldParserManager().getRootFieldParsers(getIdGroup());
		IFieldParser last = null;
		try {
			for (IFieldParser parser : roots) {
				last = parser;
				parser.parse(reader, message);
			}
		} catch (Exception e) {
			if (getFailOnError()) {
				throw e;
			} else {
				Activator.getInstance().warning("Error parsing "+last,e);
			}
		}
	}
	private boolean getFailOnError() {
		return false;//PreferenceDAO.getBoolean(PreferenceDAO.FAIL_ON_PARSE_FIELD_ERROR, Parser.DEFAUT_FAIL_ON_ERROR);
	}
}
