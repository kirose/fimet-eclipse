package com.fimet.core.ISO8583.parser;


import com.fimet.commons.data.reader.IReader;
import com.fimet.commons.data.writer.IWriter;
import com.fimet.commons.exception.FormatException;
import com.fimet.commons.utils.ByteUtils;
import com.fimet.core.entity.sqlite.FieldFormat;

/**
 * Parser for MessageFields from the message 
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class TrimFixedFieldParser extends FixedFieldParser {

	private Integer length;
	public TrimFixedFieldParser(FieldFormat fieldFormat) {
		super(fieldFormat);
		this.length = fieldFormat.getLength();
	}
	@Override
	protected byte[] parseValue(IReader reader, IMessage message) {
		if (length % 2 == 0) {
			byte[] value = converterValue.convert(reader.getBytes(length));
			reader.move(length);
			return value;
		} else {
			byte[] value = ByteUtils.subArray(converterValue.convert(reader.getBytes(length+1)), 1);
			reader.move(length+1);
			return value;
		}
	}
	@Override
	protected byte[] formatValue(IWriter writer, IMessage message, byte[] value) {
		if (length % 2 == 0) {
			value = converterValue.deconvert(value);
			writer.append(value);
		} else {
			value = converterValue.deconvert(ByteUtils.preappend(value,(byte)48));
			writer.append(value);
		}
		if ((length % 2 == 0 && value.length != length) || (length % 2 != 0 && value.length -1 != length)){
			throw new FormatException(this+" - Invalid length ("+value.length+") on field "+getIdField()+" expected fixed length: "+length);
		}
		return value;
	}
}
