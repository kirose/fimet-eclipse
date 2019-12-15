package com.fimet.core.ISO8583.adapter;

public interface IStringAdapter extends IAdapter {
	boolean isAdaptable(String message);
	byte[] readString(String message);
	String writeString(byte[] message);
}
