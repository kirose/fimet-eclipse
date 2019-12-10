package com.fimet.core.ISO8583.adapter;

import com.fimet.commons.utils.ByteUtils;

public class ExtractAdapter extends Adapter implements IStringAdapter, IByteArrayAdapter {

	private static final String REGEXP_BASE = "1[1-9][0-9]{19}.*";
	
	ExtractAdapter(int id, String name) {
		super(id, name);
	}

	@Override
	public boolean isAdaptable(byte[] message) {
		return isAdaptable(new String(message));
	}

	@Override
	public boolean isAdaptable(String message) {
		return isBase(message) || isAdditional(message);
	}
	private boolean isAdditional(String message) {
		int length = message != null ? message.length() : -1;
		if (length < 24) {
			return false;
		}
		if (!message.substring(0,24).matches("^[0-9]+$")) {
			return false;
		}
		if (message.charAt(length-1) == '\n') {
			length--;
		}
		int idAdditional = Integer.parseInt(message.substring(17,20));
		if (idAdditional < 1 || idAdditional > 30) {
			return false;
		}
		int expectedLength = Integer.parseInt(message.substring(20,24));
		return length -24 == expectedLength;
	}

	private boolean isBase(String message) {
		int ln = message != null ? message.length() + (message.charAt(message.length()-1) == '\n' ? -1 : 0) : -1;
		return (ln == 450 || ln == 500 || ln == 1000) && message.substring(0,ln).matches(REGEXP_BASE);		
	}
	@Override
	public byte[] readString(String message) {
		if (message.charAt(message.length()-1) == '\n') {
			return message.substring(0,message.length()-1).getBytes();
		} else {
			return message.getBytes();
		}
	}

	@Override
	public String writeString(byte[] message) {
		return new String(message)+"\n";
	}

	@Override
	public byte[] readByteArray(byte[] message) {
		return readString(new String(message));
	}

	@Override
	public byte[] writeByteArray(byte[] message) {
		return ByteUtils.append(message, (byte)10);
	}


}
