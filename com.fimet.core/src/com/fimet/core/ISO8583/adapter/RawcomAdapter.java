package com.fimet.core.ISO8583.adapter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fimet.commons.converter.Converter;
import com.fimet.commons.exception.AdapterException;
import com.fimet.commons.exception.ParserException;
import com.fimet.commons.utils.ByteUtils;

public class RawcomAdapter extends Adapter implements IStringAdapter, IByteArrayAdapter {
	public static final String REGEXP_RAWCOM                   = "\\[T: [0-9]{2}:[0-9]{2}:[0-9]{2}\\.[0-9]{3}\\]\\[D:[0-9A-Za-z ]+\\]\\[C:[0-9A-Za-z.* ]+\\]\\[Iap:[a-zA-Z0-9_\\- ]+\\]\\[Lp:[0-9:a-zA-Z-_.* ]+\\]\\[Rw:[0-9a-zA-Z ]+\\]\\[L:[ ]+[0-9]+](?s).+";
	public static final Pattern PATTERN_RAWCOM = Pattern.compile("\\[T: [0-9]{2}:[0-9]{2}:[0-9]{2}\\.[0-9]{3}\\]\\[D:[0-9A-Za-z ]+\\]\\[C:[0-9A-Za-z.* ]+\\]\\[Iap:[a-zA-Z0-9_\\- ]+\\]\\[Lp:[0-9:a-zA-Z-_.* ]+\\]\\[Rw:[0-9a-zA-Z ]+\\]\\[L:[ ]+[0-9]+](.+)",Pattern.DOTALL);
	RawcomAdapter(int id, String name) {
		super(id, name);
	}
	@Override
	public boolean isAdaptable(byte[] message) {
		try {
			return findStartOfMessageRawcom(message) != -1;
		} catch (Exception e) {
			return false;
		}
	}
	@Override
	public boolean isAdaptable(String message) {
		return message != null && message.matches(REGEXP_RAWCOM);
	}
	@Override
	public String writeString(byte[] message) {
		return new String(message);
	}
	@Override
	public byte[] readString(String message) {
		/**
		 *	Rawcom
		 * [T: 08:03:23.930][D: 11186][C:    1111111111111111][Iap: iap_SMART     ][Lp: 0:I12][Rw: W][L:  308]ISO0240000770110323A84802E818002000000000000100000020714032407032008032302070207020701079211111111111111111=****19038005696302816100AHEREC03        0274076109            00010001484124& 0000600124! Q100002 9 ! Q200002 04! C400012 000110403080! 0400020                     ! C000026 **** 001          0  0 0
		 */
		if (isAdaptable(message)) {
			Matcher m = PATTERN_RAWCOM.matcher(message);
			if (m.find()) {
				message = m.group(1);
				if (message.matches(REGEXP_HEX)) {
					return Converter.hexToAscii(message.getBytes());
				} else if (message.matches(REGEXP_HEX_STAR)) {
					message = message.replace("**", "0A");
					return Converter.hexToAscii(message.getBytes());
				} else {
					/*String msg = m.group(1).replace("**", newChar);
					if ()*/
					return m.group(1).getBytes();
				}
			} else {
				throw new AdapterException("Cannot adapt the message as rawcom");	
			}
		} else {
			throw new AdapterException("The message is not a rawcom message");
		}
	}
	@Override
	public byte[] readByteArray(byte[] message) {
		int start = findStartOfMessageRawcom(message);
		if (start == -1) {
			throw new ParserException("The message is not rawcom message");
		}
		int end = findEndOfMessage(message);
		byte[] newBytes = new byte[end+1-start];
		System.arraycopy(message, start, newBytes, 0, end+1-start);
		return newBytes;
	}
	private static int findStartOfMessageRawcom(byte[] bytes) {
		try {
			int i = ByteUtils.indexNextNotBlank(bytes, 0);
			// validamos que comience con [T: 08:03:23.930] 
			int ln = bytes.length;
			ByteUtils.assertByte(bytes, i++, (byte)91);//'['
			ByteUtils.assertByte(bytes, i++, (byte)84);//'T'
			ByteUtils.assertByte(bytes, i++, (byte)58);//':'
			i = ByteUtils.indexNextNotBlank(bytes, i);
			ByteUtils.assertNumeric(bytes, i++);
			ByteUtils.assertNumeric(bytes, i++);
			ByteUtils.assertByte(bytes, i++, (byte)58);//':'
			ByteUtils.assertNumeric(bytes, i++);
			ByteUtils.assertNumeric(bytes, i++);
			ByteUtils.assertByte(bytes, i++, (byte)58);//':'
			ByteUtils.assertNumeric(bytes, i++);
			ByteUtils.assertNumeric(bytes, i++);
			ByteUtils.assertByte(bytes, i++, (byte)46);//'.'
			ByteUtils.assertNumeric(bytes, i++);
			byte b;
			while (i < ln) {
				b = bytes[i];
				if (b == (byte)91 && i+1 < ln && bytes[i+1] == (byte)76 && bytes[i+2] == (byte)58) {//b == [ && b+1 == L && b+2 == :
					i +=3;
					while (i < ln && (bytes[i] == (byte)9 || bytes[i] == (byte)32)) {//b == '\t' || b == ' '
						i++;
					}
					if (i >= ln) {
						throw new AdapterException("Incomplete Rawcom message"); 
					}
					i = ByteUtils.indexNextNotNumeric(bytes, i);
					ByteUtils.assertByte(bytes, i++, (byte)93);//']'
					return i;
				}
				i++;
			}
			return -1;
		} catch (Exception e) {
			throw new AdapterException("The message is not rawcom message",e);
		}
	}
	
	private static int findEndOfMessage(byte[] bytes) {
		if (bytes == null || bytes.length == 0) {
			return -1;
		}
		int ln = bytes.length;
		int i = ln-1;
		byte b;
		while (i > 0 && ((b = bytes[i]) == (byte)9 || b == (byte)10)) {//b == '\t' || b == ' ' || b == '\n' 
			i--;
		}
		return i;
	}
	@Override
	public byte[] writeByteArray(byte[] message) {
		//[T: 10:41:15.075][D: 11003][C:    4152313541593377][Iap:            iap_BNAME][Lp:  0:I3][Rw: R][L:  733]
		byte[] header = ("[T: 00:00:00.000][D: 00000][C:    0000000000000000][Iap:            iap][Lp:  0:I0][Rw: X][L:  "+message.length+"]").getBytes();
		byte[] out = new byte[header.length + message.length];
		System.arraycopy(header, 0, out, 0, header.length);
		System.arraycopy(message, 0, out, header.length, message.length);
		return out;
	}
}
