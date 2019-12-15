package com.fimet.parser.field.impl.tpv;

import com.fimet.commons.data.reader.IReader;
import com.fimet.commons.data.writer.IWriter;
import com.fimet.commons.exception.FormatException;
import com.fimet.core.ISO8583.parser.IMessage;
import com.fimet.core.ISO8583.parser.VarFieldParser;
import com.fimet.core.entity.sqlite.FieldFormat;

public class TpvTokenVarFieldParser extends VarFieldParser {

	public TpvTokenVarFieldParser(FieldFormat fieldFormat) {
		super(fieldFormat);
	}
	@Override
	public byte[] parse(IReader reader, IMessage message) {
		reader.move(2);
		return super.parse(reader, message);
	}
	@Override
	protected byte[] formatValue(IWriter writer, IMessage message, byte[] value) {
		writer.append(key);
		int index = writer.length(); 
		writer.move(length);

		value = converterValue.deconvert(value);
		writer.append(value);
		
		int length = value.length;
		if (maxLength != null && length > maxLength) {
			throw new FormatException("Field "+this.idField+" ("+length+") Exceed MaxLength: "+ maxLength);
		}
		writer.replace(index, parserLength.format(length, this.length));
		return value;
	}
}
