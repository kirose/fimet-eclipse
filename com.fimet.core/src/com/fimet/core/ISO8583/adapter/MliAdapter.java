package com.fimet.core.ISO8583.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.fimet.commons.exception.AdapterException;

public abstract class MliAdapter extends Adapter implements IStreamAdapter, IByteArrayAdapter, IStringAdapter {
	private boolean exclusive;
	MliAdapter(int id, String name, boolean exclusive) {
		super(id, name);
		this.exclusive = exclusive;
	}
	@Override
	public boolean isAdaptable(InputStream in) {
		return false;
	}
	@Override
	public byte[] readStream(InputStream in) {
		try {
			byte[] mli = new byte[2];// ex. {0,231} -> 00E7 -> 231
			in.read(mli, 0, 2);
			int length = ((mli[0] & 0xFF)<<8)|(mli[1] & 0xFF);
			if (!exclusive) {
				length = length - 2;
			}
			byte[] message = new byte[length];
			in.read(message);
			return message;
		} catch (IOException e) {
			throw new AdapterException("Adapter error, cannot adapt InputStream",e);
		} catch (ThreadDeath e) {
			throw new AdapterException("Socket Disconnected, cannot read mli from incoming message",e);
		}
	}
	@Override
	public void writeStream(OutputStream out, byte[] message) {
		int length = message.length;
		if (!exclusive) {
			length = length + 2;
		}
		byte[] mli = {(byte)((length >> 8) & 0xFF),(byte)(length & 0xFF)};
		try {
			out.write(mli);
			out.write(message);
		} catch (IOException e) {
			throw new AdapterException("Cannot read mli from incoming message",e);
		} catch (ThreadDeath e) {
			throw new AdapterException("Socket Disconnected, cannot read mli from incoming message",e);
		}
	}
	@Override
	public boolean isAdaptable(byte[] in) {
		int length = ((in[0] & 0xFF)<<8)|(in[1] & 0xFF);
		if (!exclusive) {
			length = length - 2;
		}
		return length == in.length -2;
	}
	@Override
	public byte[] readByteArray(byte[] in) {
		int length = ((in[0] & 0xFF)<<8)|(in[1] & 0xFF);
		if (!exclusive) {
			length = length - 2;
		}
		byte[] message = new byte[length];
		System.arraycopy(in, 2, message, 0, length);
		return message;
	}
	@Override
	public byte[] writeByteArray(byte[] message) {
		int length = message.length;
		if (!exclusive) {
			length = length + 2;
		}
		byte[] out = new byte[message.length + 2];
		out[0] = (byte)((length >> 8) & 0xFF);
		out[1] = (byte)(length & 0xFF);
		System.arraycopy(message, 0, out, 2, message.length);
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
