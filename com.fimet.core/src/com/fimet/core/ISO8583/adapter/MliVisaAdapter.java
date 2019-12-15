package com.fimet.core.ISO8583.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.fimet.commons.exception.AdapterException;

public abstract class MliVisaAdapter extends Adapter implements IStreamAdapter, IByteArrayAdapter, IStringAdapter {
	private boolean exclusive; 
	MliVisaAdapter(int id, String name, boolean exclusive) {
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
			byte[] mli = new byte[4];
			in.read(mli, 0, 4);
			//int length = Integer.parseInt(new String(Converter.asciiToHex(new byte[] {mli[0],mli[1]})), 16);
			int length = ((mli[0] & 0xFF)<<8)|(mli[1] & 0xFF);
			if (!exclusive) {
				length = length - 4;
			}
			byte[] message = new byte[length];
			in.read(message);
			return message;
		} catch (IOException e) {
			throw new AdapterException("Cannot read mli from incoming message",e);
		} catch (ThreadDeath e) {
			throw new AdapterException("Socket Disconnected, cannot read mli from incoming message",e);
		}
	}
	@Override
	public void writeStream(OutputStream out, byte[] message) {
		int length = message.length;
		if (!exclusive) {
			length = length + 4;
		}
		byte[] mli = {(byte)((length >> 8) & 0xFF),(byte)(length & 0xFF),(byte)0,(byte)0};
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
		if (in == null || in.length < 3 || in[2] != (byte)0 || in[3] != (byte)0) {
			return false;
		}
		int length = ((in[0] & 0xFF)<<8)|(in[1] & 0xFF);
		if (!exclusive) {
			length = length - 2;
		}
		return length == in.length - 4;
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
		byte[] out = new byte[message.length + 4];
		out[0] = (byte)((length >> 8) & 0xFF);
		out[1] = (byte)(length & 0xFF);
		out[2] = (byte)0;
		out[3] = (byte)0;
		System.arraycopy(message, 0, out, 4, message.length);
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
