package com.fimet.core.layout.parser;

import java.util.List;

import com.fimet.commons.data.writer.IWriter;
import com.fimet.commons.data.writer.impl.ByteArrayWriter;
import com.fimet.core.Activator;
import com.fimet.core.ISO8583.parser.AbstractBaseParser;
import com.fimet.core.ISO8583.parser.IFieldParser;
import com.fimet.core.ISO8583.parser.IMessage;
import com.fimet.core.entity.sqlite.Parser;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public abstract class AbstractMessageExtractFormater extends AbstractBaseParser {

	public AbstractMessageExtractFormater(Parser entity) {
		super(entity);
	}

	@Override
	public byte[] formatMessage(IMessage msg) {
		IWriter writer = new ByteArrayWriter();
		formatFields(writer, msg);
		byte[] iso = getConverter().deconvert(writer.getBytes());
		return iso;
	}
	protected void formatFields(IWriter writer, IMessage msg) {
		List<IFieldParser> roots = getFieldParserManager().getRootFieldParsers(getIdGroup());
		for (IFieldParser parser : roots) {
			try {
				parser.format(writer, msg);
			} catch (Exception e) {
				Activator.getInstance().error(this+" error formating field "+parser.getIdField(),e);
				throw e;
			}
		}
	}
}
