package com.fimet.parser.rawcom;

public class Rawcom {
	byte[] bytes;
	int startMessage;
	public Rawcom(byte[] bytes, int startMessage) {
		super();
		this.bytes = bytes;
		this.startMessage = startMessage;
	}
	public byte[] getBytes() {
		return bytes;
	}
	public byte[] getMessage() {
		byte[] message = new byte[bytes.length-startMessage];
		System.arraycopy(bytes, startMessage, message, 0, message.length);
		return message;
	}
}
