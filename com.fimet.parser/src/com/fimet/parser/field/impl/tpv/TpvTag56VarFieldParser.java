package com.fimet.parser.field.impl.tpv;

import com.fimet.commons.data.reader.IReader;
import com.fimet.commons.data.writer.IWriter;
import com.fimet.core.ISO8583.parser.IMessage;
import com.fimet.core.ISO8583.parser.VarFieldParser;
import com.fimet.core.entity.sqlite.FieldFormat;

public class TpvTag56VarFieldParser extends VarFieldParser {

	public TpvTag56VarFieldParser(FieldFormat fieldFormat) {
		super(fieldFormat);
	}
	@Override
	public byte[] parse(IReader reader, IMessage message) {
		reader.move(2);
		return super.parse(reader,message);
	}
	@Override
	public byte[] format(IWriter writer, IMessage message) {
		writer.append(key.getBytes());
		return super.format(writer, message);
	}
}
