package com.fimet.core.ISO8583.parser;


import com.fimet.commons.data.reader.IReader;
import com.fimet.commons.data.writer.IWriter;
import com.fimet.commons.exception.FormatException;
import com.fimet.core.entity.sqlite.FieldFormat;

/**
 * Parser for MessageFields from the message 
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class FixedFieldParser extends AbstractFieldParser {

	private Integer length;
	public FixedFieldParser(FieldFormat fieldFormat) {
		super(fieldFormat);
		this.length = fieldFormat.getLength();
	}
	@Override
	protected byte[] parseValue(IReader reader, IMessage message) {
		byte[] value = converterValue.convert(reader.getBytes(length));
		reader.move(length);
		return value;
	}
	@Override
	protected byte[] formatValue(IWriter writer, IMessage message, byte[] value) {
		value = converterValue.deconvert(value);
		writer.append(value);
		if (value.length != length){
			throw new FormatException(this+" - Invalid length ("+value.length+") on field "+getIdField()+" expected fixed length: "+length);
		}
		return value;
	}
	public boolean isFixed() {
		return true;
	}
	public boolean isValidLength(String value) {
		return converterValue.deconvert(value.getBytes()).length == length;
	}
}
