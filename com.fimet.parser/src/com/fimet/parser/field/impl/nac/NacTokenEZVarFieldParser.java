package com.fimet.parser.field.impl.nac;

import com.fimet.commons.data.writer.IWriter;
import com.fimet.commons.exception.ParserException;
import com.fimet.commons.utils.ByteUtils;
import com.fimet.commons.utils.EncryptionUtils;
import com.fimet.core.IPreferencesManager;
import com.fimet.core.Manager;
import com.fimet.core.ISO8583.parser.IMessage;
import com.fimet.core.entity.sqlite.FieldFormat;

public class NacTokenEZVarFieldParser extends NacTokenVarFieldParser {

	private static final String DEFAULT_BDK = "0123456789ABCDEFFEDCBA9876543210";
	private static IPreferencesManager preferencesManager = Manager.get(IPreferencesManager.class);
	public NacTokenEZVarFieldParser(FieldFormat fieldFormat) {
		super(fieldFormat);
	}
	@Override
	protected byte[] posParseValue(byte[] value, IMessage message) {
		value = decrypt(value, message);
		return super.posParseValue(value, message);
	}
	@Override
	protected byte[] preFormatValue(IWriter writer, IMessage message) {
		byte[] value = super.preFormatValue(writer, message);
		return crc32(encrypt(last4Digits(value), message));
	}
	private byte[] encrypt(byte[] value, IMessage message) {
		if (value == null || value.length != 98) {
			throw new ParserException("Cannot encrypt data from token EZ, data must be ascii");
		}
		String fKeySerialNumber = new String(ByteUtils.subArray(value, 0, 20));

		byte[] encrypt = EncryptionUtils.encrypt(
			new String(ByteUtils.subArray(value, 38, 86)),
			fKeySerialNumber, 
			preferencesManager.getString(IPreferencesManager.BDK, DEFAULT_BDK)
		).substring(0,48).getBytes();
		
		return ByteUtils.concat(
			ByteUtils.subArray(value, 0,38),
			encrypt,
			ByteUtils.subArray(value, 86,98)
		);
	}
	private byte[] crc32(byte[] value) {
		return ByteUtils.concat(
			ByteUtils.subArray(value, 0, 90),
			EncryptionUtils.crc32hex(new String(ByteUtils.subArray(value, 38, 86))).getBytes()
		);
	}
	private byte[] last4Digits(byte[] value) {
		return ByteUtils.concat(
			ByteUtils.subArray(value, 0, 86),
			ByteUtils.subArray(value, 50, 54),
			ByteUtils.subArray(value, 90, 98)
		);
	}
	private byte[] decrypt(byte[] value, IMessage message) {
		if (value == null || value.length != 98) {
			throw new ParserException("Cannot decript data from token EZ, data must be ascii");
		}
		String fKeySerialNumber = new String(ByteUtils.subArray(value, 0, 20));
		byte[] decrypt = EncryptionUtils.decrypt(
			new String(ByteUtils.subArray(value, 38, 86)),
			fKeySerialNumber,
			preferencesManager.getString(IPreferencesManager.BDK, DEFAULT_BDK)
		).substring(0,48).getBytes();
		return ByteUtils.concat(
			ByteUtils.subArray(value, 0,38),
			decrypt,
			ByteUtils.subArray(value, 86,98)
		);
	}
}
