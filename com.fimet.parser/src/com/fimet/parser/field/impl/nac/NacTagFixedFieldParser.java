package com.fimet.parser.field.impl.nac;

import com.fimet.commons.converter.Converter;
import com.fimet.commons.converter.IConverter;
import com.fimet.commons.data.reader.IReader;
import com.fimet.commons.data.writer.IWriter;
import com.fimet.commons.numericparser.INumericParser;
import com.fimet.commons.numericparser.NumericParser;
import com.fimet.core.ISO8583.parser.FixedFieldParser;
import com.fimet.core.ISO8583.parser.IMessage;
import com.fimet.core.entity.sqlite.FieldFormat;

public class NacTagFixedFieldParser extends FixedFieldParser {

	protected final IConverter  converterLength;
	protected final INumericParser parserLength;
	public NacTagFixedFieldParser(FieldFormat fieldFormat) {
		super(fieldFormat);
		this.converterLength = Converter.get(fieldFormat.getIdConverterLength());
		this.parserLength = NumericParser.get(fieldFormat.getIdParserLength());
	}
	@Override
	public byte[] parse(IReader reader, IMessage message) {
		reader.move(converterLength.deconvert(key.getBytes()).length+2);
		return super.parse(reader, message);
	}
	@Override
	public byte[] format(IWriter writer, IMessage message) {
		writer.append(converterLength.deconvert(key.getBytes()));
		int index = writer.length();
		writer.move(2);
		byte[] value = super.format(writer, message);
		writer.replace(index, converterLength.deconvert(parserLength.format(value.length, 2)));
		return value;
	}
}
