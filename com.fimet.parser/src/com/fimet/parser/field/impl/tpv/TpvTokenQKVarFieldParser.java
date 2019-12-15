package com.fimet.parser.field.impl.tpv;

import com.fimet.commons.converter.Converter;
import com.fimet.commons.data.writer.IWriter;
import com.fimet.commons.exception.ParserException;
import com.fimet.commons.utils.ByteUtils;
import com.fimet.commons.utils.EncryptionUtils;
import com.fimet.core.IPreferencesManager;
import com.fimet.core.Manager;
import com.fimet.core.ISO8583.parser.IMessage;
import com.fimet.core.entity.sqlite.FieldFormat;

public class TpvTokenQKVarFieldParser extends TpvTokenVarFieldParser {

	private static final String DEFAULT_BDK = "0123456789ABCDEFFEDCBA9876543210";
	private static IPreferencesManager preferencesManager = Manager.get(IPreferencesManager.class);
	public TpvTokenQKVarFieldParser(FieldFormat fieldFormat) {
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
		return encrypt(value, message);
	}
	private byte[] decrypt(byte[] value, IMessage message) {
		String fTokenQKEncrypted = new String(value);
		String fKeySerialNumber = message.getValue(idFieldParent+".EZ.1");// El Token EZ debe ser parseado primero
		if (fKeySerialNumber == null) {
			throw new ParserException("Token EZ.1 (Key serial Number) is required when include token QK");
		}
		String dataEncrypted = new String(ByteUtils.subArray(fTokenQKEncrypted.getBytes(), 0,80));
		byte[] crc32 = ByteUtils.subArray(fTokenQKEncrypted.getBytes(), 80, 96);
		String decrypt = EncryptionUtils.decrypt(dataEncrypted, fKeySerialNumber, preferencesManager.getString(IPreferencesManager.BDK, DEFAULT_BDK));
		message.setField(idField, ByteUtils.concat(decrypt.getBytes(), crc32));
		return ByteUtils.concat(decrypt.getBytes(), crc32);
	}
	private byte[] encrypt(byte[] bytes, IMessage message) {
		String fTokenQkDecrypted = new String(bytes);
		String fKeySerialNumber = message.getValue(idFieldParent+".EZ.1");
		if (fKeySerialNumber == null) {
			throw new ParserException("Token EZ.1 (Key serial Number) is required when include token QK");
		}
		String encrypt = EncryptionUtils.encrypt(fTokenQkDecrypted, fKeySerialNumber, preferencesManager.getString(IPreferencesManager.BDK, DEFAULT_BDK));
		encrypt = encrypt.substring(0,80);
		String crc32 = EncryptionUtils.crc32Unmarshall(encrypt);
		return ByteUtils.concat(encrypt.getBytes(), Converter.asciiToHex(crc32.getBytes()));
	}
}
