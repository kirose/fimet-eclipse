package com.fimet.parser.field.impl.visa;

import java.util.ArrayList;
import java.util.List;

import com.fimet.commons.converter.Converter;
import com.fimet.commons.data.reader.IReader;
import com.fimet.commons.data.reader.impl.ByteArrayReader;
import com.fimet.commons.data.writer.IWriter;
import com.fimet.core.ISO8583.parser.IMessage;
import com.fimet.core.ISO8583.parser.VarFieldParser;
import com.fimet.core.entity.sqlite.FieldFormat;

public class Visa126VarFieldParser extends VarFieldParser {

	private static final String EMPTY_BITMAP = "0000000000000000000000000000000000000000000000000000000000000000";
	
	public Visa126VarFieldParser(FieldFormat fieldFormat) {
		super(fieldFormat);
	}
	@Override
	protected void parseChilds(byte[] value, IMessage message) {
		if (childs != null) {
			IReader reader = new ByteArrayReader(value);//Bytes in ebcdict
			if (reader.hasNext()) {
				List<Integer> bitmap = parseBitmap(reader);
				for (Integer index : bitmap) {
					getFieldParserManager().getFieldParser(getGroup(),idField+"."+index).parse(reader, message);
				}
			}
		}
	}
	public List<Integer> parseBitmap(IReader reader) {
		List<Integer> bitmap = new ArrayList<>();
		String bm = reader.hex(16).toBinary().toString();
		for (int i = 0; i < bm.length(); i++) {
			if (bm.charAt(i) == '1') {
				bitmap.add(i+1);
			}
		}
		return bitmap;
	}
	@Override
	protected void formatChilds(IWriter writer, IMessage message) {
		formatBitmap(writer, message);
		for (String idChild : message.getIdChildren(idField)) {
			getFieldParserManager().format(message, idChild, writer);
		}
	}
	private void formatBitmap(IWriter writer, IMessage message) {
		StringBuilder bitmap = new StringBuilder().append(EMPTY_BITMAP);
		int index = 0;
		for (String idField : message.getIdChildren(idField)) {
			index = Integer.parseInt(idField.substring(idField.lastIndexOf('.')+1));
			bitmap.replace(index-1, index, "1");
		}
		writer.append(Converter.binaryToHex(bitmap.toString().getBytes()));
	}
}
