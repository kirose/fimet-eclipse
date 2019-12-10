package com.fimet.parser.field.impl.mc;


import com.fimet.commons.data.reader.IReader;
import com.fimet.commons.data.reader.impl.ByteArrayReader;
import com.fimet.commons.data.writer.IWriter;
import com.fimet.commons.exception.FormatException;
import com.fimet.commons.exception.ParserException;
import com.fimet.core.ISO8583.parser.IMessage;
import com.fimet.core.ISO8583.parser.VarFieldParser;
import com.fimet.core.entity.sqlite.FieldFormat;
import com.fimet.parser.Activator;

public class MC48VarFieldParser extends VarFieldParser {

	public MC48VarFieldParser(FieldFormat fieldFormat) {
		super(fieldFormat);
	}
	@Override
	protected void parseChilds(byte[] value, IMessage message) {
		if (childs != null) {
			try {
				IReader reader = new ByteArrayReader(value);// Tags
				getFieldParserManager().getFieldParser(getGroup(),idField+"."+"tcc").parse(reader, message);
				parseTags(message, reader);
			} catch (Exception e) {
				if (getFailOnError()) {
					throw e;
				} else {
					Activator.getInstance().warning("Parsing tags "+idField,e);
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
		String nextTag = getNextTag(reader);
		if (nextTag == null) {
			throw new ParserException(this+" unknow Tag starts with: "+reader.toString().substring(0,5)+".\nTags declared: "+childs);	
		}
		return getFieldParserManager().getFieldParser(getGroup(),idField+"."+nextTag).parse(reader, message);
	}
	private String getNextTag(IReader reader) {
		for (String tag : childs) {
			if (reader.matcher(tag).asByte()) {
				return tag;
			}
		}
		return null;
	}
	@Override
	protected void formatChilds(IWriter writer, IMessage message) {
		for (String idChild : message.getIdChildren(idField)) {
			validateTag(idChild);
			getFieldParserManager().format(message, idChild, writer);
		}
	}
	private void validateTag(String idField) {
		String tag = idField.substring(idField.lastIndexOf('.')+1);
		for (String child : childs) {
			if (child.equals(tag)) {
				return;
			}
		}
		throw new FormatException("Unexpected Tag '"+tag+"', Tags declared: "+childs);
	}
}
