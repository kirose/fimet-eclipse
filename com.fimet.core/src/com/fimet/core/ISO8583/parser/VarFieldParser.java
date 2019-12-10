package com.fimet.core.ISO8583.parser;


import com.fimet.commons.converter.Converter;
import com.fimet.commons.converter.IConverter;
import com.fimet.commons.data.reader.IReader;
import com.fimet.commons.data.writer.IWriter;
import com.fimet.commons.exception.FormatException;
import com.fimet.commons.numericparser.INumericParser;
import com.fimet.commons.numericparser.NumericParser;
import com.fimet.core.Activator;
import com.fimet.core.entity.sqlite.FieldFormat;

/**
 * Parser for MessageFields from the message 
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class VarFieldParser extends AbstractFieldParser {

	protected Integer maxLength;
	protected final IConverter  converterLength;
	protected final INumericParser parserLength;
	
	public VarFieldParser(FieldFormat fieldFormat) {
		super(fieldFormat);
		this.maxLength = fieldFormat.getMaxLength();
		this.converterLength = Converter.get(fieldFormat.getIdConverterLength());
		this.parserLength = NumericParser.get(fieldFormat.getIdParserLength());
	}
	@Override
	protected byte[] parseValue(IReader reader, IMessage message) {
		int length = parserLength.parse(converterLength.convert(reader.read(this.length)));
		byte[] bytes = reader.read(length);
		if (bytes.length != length)
			Activator.getInstance().warning(this+", expected length "+length+" current length: "+bytes.length);
		return converterValue.convert(bytes);
	}
	@Override
	protected byte[] formatValue(IWriter writer, IMessage message, byte[] value) {
		int index = writer.length(); 
		writer.move(this.length);

		value = converterValue.deconvert(value);
		writer.append(value);
		
		int length = value.length;
		if (maxLength != null && length > maxLength) {
			throw new FormatException("Field "+this.idField+" ("+length+") Exceed MaxLength: "+ maxLength);
		}
		writer.replace(index, converterLength.deconvert(parserLength.format(length, this.length)));
		return value;
	}
	public boolean isFixed() {
		return false;
	}
	public int getMaxLength() {
		return maxLength;
	}
	public boolean isValidLength(String value) {
		try {
			return converterValue.deconvert(value.getBytes()).length <= maxLength;
		} catch (Exception e) {
			return false;
		}
	}
}
