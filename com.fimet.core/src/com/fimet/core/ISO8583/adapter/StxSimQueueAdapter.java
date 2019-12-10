package com.fimet.core.ISO8583.adapter;

public class StxSimQueueAdapter extends Adapter implements IByteArrayAdapter, IStringAdapter {

	StxSimQueueAdapter(int id, String name) {
		super(id, name);
	}
	@Override
	public boolean isAdaptable(byte[] in) {
		if (in == null || in.length == 0 || in[0] != (byte)2) {
			return false;
		}
		int last = in.length-1;
		while (last > 0) {
			if (in[last] == (byte)10) {// \n
				last--;
				if (last >= 0 && in[last] == (byte)13) {// \r
					last--;
				}
			} else {
				break;
			}
		}
		return last > 0 && in[last] == (byte)3;
	}

	@Override
	public byte[] readByteArray(byte[] in) {
		int last = in.length-1;
		while (last > 0) {
			if (in[last] == (byte)10) {// \n
				last--;
				if (last >= 0 && in[last] == (byte)13) {// \r
					last--;
				}
			} else {
				break;
			}
		}
		byte[] message = new byte[last-1];
		System.arraycopy(in, 1, message, 0, last-1);
		return message;
	}

	@Override
	public byte[] writeByteArray(byte[] message) {
		byte[] out = new byte[message.length+2];
		out[0] = (byte)2;
		out[out.length-1] = (byte)3;
		System.arraycopy(message, 0, out, 1, message.length);
		return out;
	}
	@Override
	public boolean isAdaptable(String message) {
		return isAdaptable(message.getBytes());
	}
	@Override
	public byte[] readString(String message) {
		return readByteArray(message.getBytes());
	}
	@Override
	public String writeString(byte[] message) {
		return new String(writeByteArray(message));
	}
}
