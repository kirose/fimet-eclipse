package com.fimet.core.ISO8583.parser;


import com.fimet.commons.data.reader.IReader;
import com.fimet.commons.data.writer.IWriter;
import com.fimet.commons.exception.FormatException;
import com.fimet.commons.utils.ByteUtils;
import com.fimet.core.Activator;
import com.fimet.core.entity.sqlite.FieldFormat;

/**
 * Useful for visa and tpv's
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class TrimVarFieldParser extends VarFieldParser {

	public TrimVarFieldParser(FieldFormat fieldFormat) {
		super(fieldFormat);
	}
	@Override
	protected byte[] parseValue(IReader reader, IMessage mesage) {
		int length = parserLength.parse(converterLength.convert(reader.read(this.length)));
		if (length % 2 == 0) {
			byte[] bytes = reader.read(length);
			if (bytes.length != length)
				Activator.getInstance().warning(this+", expected length "+length+" current length: "+bytes.length);
			return converterValue.convert(bytes);
		} else {
			byte[] bytes = reader.read(length+1);
			if (bytes.length != length+1)
				Activator.getInstance().warning(this+", expected length "+(length+1)+" current length: "+bytes.length);
			return ByteUtils.subArray(converterValue.convert(bytes),1);
		}
	}
	@Override
	protected byte[] formatValue(IWriter writer, IMessage message, byte[] value) {
		int index = writer.length(); 
		writer.move(length);
		int length = value.length;
		if (length % 2 == 0) {
			value = converterValue.deconvert(value);
			writer.append(value);
			writer.replace(index, parserLength.format(length, this.length));
		} else {
			value = converterValue.deconvert(ByteUtils.preappend(value,(byte)48));
			writer.append(value);
			writer.replace(index, parserLength.format(length, this.length));			
		}
		if (maxLength != null && length > maxLength) {
			throw new FormatException("Field "+this.idField+" ("+length+") Exceed MaxLength: "+ maxLength);
		}
		return value;
	}
}
