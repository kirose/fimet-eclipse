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

public class VisaDatasetsVarFieldParser extends VarFieldParser {

	public VisaDatasetsVarFieldParser(FieldFormat fieldFormat) {
		super(fieldFormat);
	}
	protected void parseChilds(byte[] value, IMessage message) {
		if (childs != null && value != null) {
			try {
				IReader reader = new ByteArrayReader(value);
				if (!reader.hasNext()) {
					return;
				}
				parseDatasets(message, reader);
			} catch (Exception e) {
				if (getFailOnError()) {
					throw e;
				} else {
					Activator.getInstance().warning("Parsing datasets "+idField,e);
				}
			}
		}
	}
	private void parseDatasets(IMessage message, IReader reader) {
		if (!reader.hasNext()) {
			return;
		}
		while (reader.hasNext()) {
			parseDataset(reader, message);
		}
	}
	private byte[] parseDataset(IReader reader, IMessage message) {
		String nextDataset = getNextDataset(reader);
		if (nextDataset == null) {
			throw new ParserException("Unknow dataset starts with: "+reader.toString().substring(0,5)+". Datasets declared: "+childs);	
		}
		return getFieldParserManager().getFieldParser(getGroup(),idField+"."+nextDataset).parse(reader, message);
	}
	private String getNextDataset(IReader reader) {
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
			validateDataset(tag);
			getFieldParserManager().format(message, idField, writer);
		}
	}
	private void validateDataset(String dataset) {
		for (String child : childs) {
			if (child.equals(dataset)) {
				return;
			}
		}
		throw new FormatException("Unexpected dataset '"+dataset+"', Dataset declared: "+childs);
	}
}
