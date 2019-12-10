package com.fimet.parser.msg.iso.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.fimet.commons.data.reader.IReader;
import com.fimet.commons.data.reader.impl.ConverterBuilder;
import com.fimet.commons.data.writer.IWriter;
import com.fimet.commons.exception.FormatException;
import com.fimet.commons.utils.StringUtils;
import com.fimet.core.ISO8583.parser.AbstractMessageParser;
import com.fimet.core.ISO8583.parser.Message;

public class DiscoverParser extends AbstractMessageParser {
	public DiscoverParser(com.fimet.core.entity.sqlite.Parser entity) {
		super(entity);
	}
	@Override
	protected void formatHeader(IWriter writer, Message msg) {
		if (msg.getMti().length() != 4) {
			throw new FormatException("MTI invalid expected length 4 found('"+msg.getMti().length()+"'): '"+msg.getMti()+"'");
		}
		writer.append(msg.getMti().getBytes());
	}
	@Override
	protected void parseHeader(IReader reader, Message msg) {
		String mti = reader.ascii(4).toString();
		msg.setHeader("");
		msg.setMti(mti);
	}
	@Override
	protected void formatBitmap(IWriter writer, Message msg) {
		String bitmap = buildBitmap(msg);
		String bitmapHex = StringUtils.leftPad(new BigInteger(bitmap,2).toString(16).toUpperCase(),bitmap.length()/4,'0');
		writer.append(new ConverterBuilder(bitmapHex.getBytes(), ConverterBuilder.TYPE_ASCII).asHex().toAscii().toBytes());
	}
	@Override
	protected int[] parseBitmap(IReader reader) {
		List<Integer> bitmap = new ArrayList<>();
		String bm;
		int noField = 2;
		do {
			bm = reader.ascii(8).toHex().toBinary().toString();
			for (int i = 1; i < bm.length(); i++) {
				if (bm.charAt(i) == '1') {
					bitmap.add(noField);
				}
				noField++;
			}
			noField++;
		} while (bm.charAt(0) == '1');
		int[] bitmapArray = new int[bitmap.size()];
		int i = 0;
		for (Integer id : bitmap) {
			bitmapArray[i++] = id;
		}
		return bitmapArray;
	}
}
