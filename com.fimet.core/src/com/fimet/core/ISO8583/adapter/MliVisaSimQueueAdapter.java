package com.fimet.core.ISO8583.adapter;


public abstract class MliVisaSimQueueAdapter extends Adapter implements IByteArrayAdapter, IStringAdapter {
	private boolean exclusive; 
	MliVisaSimQueueAdapter(int id, String name, boolean exclusive) {
		super(id, name);
		this.exclusive = exclusive;
	}
	@Override
	public boolean isAdaptable(byte[] in) {
		if (in == null || in.length < 3 || in[2] != (byte)0 || in[3] != (byte)0) {
			return false;
		}
		int length = ((in[0] & 0xFF)<<8)|(in[1] & 0xFF);
		if (!exclusive) {
			length = length - 2;
		}
		int last = in.length-1;
		while (last >= 0) {
			if (in[last] == (byte)10) {// \n
				last--;
				if (last >= 0 && in[last] == (byte)13) {// \r
					last--;
				}
			} else {
				break;
			}
		}
		return length == last -3;
	}
	@Override
	public byte[] readByteArray(byte[] in) {
		int length = ((in[0] & 0xFF)<<8)|(in[1] & 0xFF);
		if (!exclusive) {
			length = length - 2;
		}
		byte[] message = new byte[length];
		System.arraycopy(in, 4, message, 0, length);
		return message;
	}
	@Override
	public byte[] writeByteArray(byte[] message) {
		int length = message.length;
		if (!exclusive) {
			length = length + 2;
		}
		byte[] out = new byte[message.length + 4 + 1];
		out[0] = (byte)((length >> 8) & 0xFF);
		out[1] = (byte)(length & 0xFF);
		out[2] = (byte)0;
		out[3] = (byte)0;
		System.arraycopy(message, 0, out, 4, message.length);
		out[out.length - 1] = (byte)10;
		return out;
	}
	public boolean isAdaptable(String message) {
		return isAdaptable(message.getBytes());
	}
	public byte[] readString(String message) {
		return readByteArray(message.getBytes());
	}
	public String writeString(byte[] message) {
		return new String(writeByteArray(message));
	}
}
