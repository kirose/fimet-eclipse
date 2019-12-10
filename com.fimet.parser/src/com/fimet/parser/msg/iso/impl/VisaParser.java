package com.fimet.parser.msg.iso.impl;

import com.fimet.commons.converter.Converter;
import com.fimet.commons.data.reader.IReader;
import com.fimet.commons.data.writer.IWriter;
import com.fimet.commons.exception.FormatException;
import com.fimet.core.ISO8583.parser.AbstractMessageParser;
import com.fimet.core.ISO8583.parser.Message;

public class VisaParser extends AbstractMessageParser {
	public VisaParser(com.fimet.core.entity.sqlite.Parser entity) {
		super(entity);
	}

	@Override
	protected void formatHeader(IWriter writer, Message msg) {
		if (msg.getHeader().length() <= 0) {
			throw new FormatException("ISO header section invalid (length = 0)");
		}
		if (msg.getMti().length() != 4) {
			throw new FormatException("MTI invalid expected length 4 found('"+msg.getMti().length()+"'): '"+msg.getMti()+"'");
		}
		String header = msg.getHeader();
		int ln = (header.length()/2+1);
		String value = String.format("%02X", ln)+header+msg.getMti();
		writer.append(value.getBytes());
	}
	@Override
	protected void formatBitmap(IWriter writer, Message msg) {
		String bitmap = buildBitmap(msg);
		writer.append(Converter.binaryToHex(bitmap.getBytes()));
	}
	@Override
	protected void parseHeader(IReader reader, Message msg) {
		int lnHeader = reader.ascii(2).asHex().toInt(16);// 16010200EE0000000000000000000000000000000000
		String header = reader.ascii((lnHeader-1)*2).toString();
		/*String flagAndFormat = header.substring(0,2);
		String textFormat = header.substring(2,4);
		String totalMessageLength = header.substring(4,8);
		String destinationStationId = header.substring(4,8);
		String sourceStationId = header.substring(8,14);
		String roundTripControlInformation = header.substring(14,16);
		String baseIFlags = header.substring(16,20);
		String messageStatusFlags = header.substring(20,26);
		String batchNumber = header.substring(26,28);
		String reserved = header.substring(28,34);
		String userInformation = header.substring(34,36);*/
		msg.setHeader(header);
		msg.setMti(reader.ascii(4).toString());
	}
}
