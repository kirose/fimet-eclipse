package com.fimet.parser.field.impl.visa;


import com.fimet.commons.data.reader.IReader;
import com.fimet.commons.data.reader.impl.ByteArrayReader;
import com.fimet.commons.data.writer.IWriter;
import com.fimet.commons.exception.FormatException;
import com.fimet.commons.exception.ParserException;
import com.fimet.core.ISO8583.parser.IMessage;
import com.fimet.core.ISO8583.parser.VarFieldParser;
import com.fimet.core.entity.sqlite.FieldFormat;
import com.fimet.parser.Activator;

public class VisaDatasetVarFieldParser extends VarFieldParser {

	public VisaDatasetVarFieldParser(FieldFormat fieldFormat) {
		super(fieldFormat);
	}
	@Override
	public byte[] parse(IReader reader, IMessage message) {
		reader.move(key.length());
		return super.parse(reader, message);
	}
	@Override
	public byte[] format(IWriter writer, IMessage message) {
		writer.append(converterValue.deconvert(key.getBytes()));
		return super.format(writer, message);
	}
	protected void parseChilds(byte[] value, IMessage message) {
		if (childs != null && value != null) {
			try {
				IReader reader = new ByteArrayReader(value);
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
		if (!reader.hasNext()) {
			return;
		}
		while (reader.hasNext()) {
			parseTag(reader, message);
		}
	}
	private byte[] parseTag(IReader reader, IMessage message) {
		String nextTag = getNextTag(reader);
		if (nextTag == null) {
			throw new ParserException("Unknow Tag starts with: "+reader.toString().substring(0,5)+".Tags declared: "+childs);	
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
		for (String idField : message.getIdChildren(idField)) {
			String tag = idField.substring(idField.lastIndexOf('.')+1);
			validateTag(tag);
			getFieldParserManager().format(message, idField, writer);
		}
	}
	private void validateTag(String tag) {
		for (String child : childs) {
			if (child.equals(tag)) {
				return;
			}
		}
		throw new FormatException("Unexpected Tag '"+tag+"', Tags declared: "+childs);
	}
}
