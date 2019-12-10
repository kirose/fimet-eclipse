package com.fimet.parser.field.impl.nac;

import com.fimet.commons.data.reader.IReader;
import com.fimet.commons.data.writer.IWriter;
import com.fimet.commons.exception.FormatException;
import com.fimet.core.ISO8583.parser.IMessage;
import com.fimet.core.ISO8583.parser.VarFieldParser;
import com.fimet.core.entity.sqlite.FieldFormat;

public class NacTokenVarFieldParser extends VarFieldParser {

	public NacTokenVarFieldParser(FieldFormat fieldFormat) {
		super(fieldFormat);
	}
	@Override
	protected byte[] parseValue(IReader reader, IMessage message) {
		reader.assertChar('!');
		reader.assertChar(' ');
		reader.move(2);
		int length = parserLength.parse(converterLength.convert(reader.getBytes(this.length)));
		reader.move(this.length);
		reader.assertChar(' '); 
		byte[] value = reader.read(length);
		message.setField(idField, converterValue.convert(value));
		return value;
	}

	@Override
	protected byte[] formatValue(IWriter writer, IMessage message, byte[] value) {
		writer.append("! ");
		writer.append(key);
		int index = writer.length(); 
		writer.move(length);
		writer.append(" ");

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
