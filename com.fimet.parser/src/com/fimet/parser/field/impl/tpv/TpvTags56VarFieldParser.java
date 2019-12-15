package com.fimet.parser.field.impl.tpv;


import com.fimet.commons.data.reader.IReader;
import com.fimet.commons.data.reader.impl.ByteArrayReader;
import com.fimet.commons.data.writer.IWriter;
import com.fimet.core.ISO8583.parser.IMessage;
import com.fimet.core.ISO8583.parser.VarFieldParser;
import com.fimet.core.entity.sqlite.FieldFormat;
import com.fimet.parser.Activator;

public class TpvTags56VarFieldParser extends VarFieldParser {

	public TpvTags56VarFieldParser(FieldFormat fieldFormat) {
		super(fieldFormat);
	}
	protected void parseChilds(byte[] value, IMessage message) {
		if (childs != null && message.getField(idField) != null) {
			try {
				IReader reader = new ByteArrayReader(message.getField(idField));
				if (!reader.hasNext()) {
					return;
				}
				parseTags(message, reader);
			} catch (Exception e) {
				if (getFailOnError()) {
					throw e;
				} else {
					Activator.getInstance().warning("Parsing tokens "+idField,e);
				}
			}
		}
	}
	private void parseTags(IMessage message, IReader reader) {
		while (reader.hasNext()) {
			parseTag(reader, message);
		}
	}
	private byte[] parseTag(IReader reader, IMessage message) {
		String nextTag = new String(reader.getBytes(2));
		return getFieldParserManager().getFieldParser(getGroup(),idField+"."+nextTag).parse(reader, message);
	}
	@Override
	protected void formatChilds(IWriter writer, IMessage message) {
		for (String idChild : message.getIdChildren(idField)) {
			getFieldParserManager().format(message, idChild, writer);
		}
	}
}
