package com.fimet.core.ISO8583.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.fimet.commons.data.writer.IWriter;
import com.fimet.commons.data.writer.impl.ByteArrayWriter;
import com.fimet.commons.exception.AdapterException;

public class StxAdapter extends Adapter implements IStreamAdapter, IByteArrayAdapter, IStringAdapter {

	StxAdapter(int id, String name) {
		super(id, name);
	}
	@Override
	public boolean isAdaptable(InputStream in) {
		return false;
	}
	@Override
	public byte[] readStream(InputStream in) {
		try {
			int stx = in.read();
			if (stx == -1 || ((byte)stx) != ((byte)2)) {
				throw new AdapterException("Expected start of text (0x02) insted found: "+stx+" '"+((char)(byte)stx)+"'");	
			}
			IWriter writer = new ByteArrayWriter();
			byte b;
			int i;
			while ((i = in.read()) != -1) {
				b = (byte)i;
				if (b == (byte)3) {
					break;
				} else {
					writer.append(b);
				}
			}
			return writer.getBytes();
		} catch (IOException e) {
			throw new AdapterException("Adapter error, cannot adapt InputStream",e);
		} catch (ThreadDeath e) {
			throw new AdapterException("Socket Disconnected, cannot read incoming message",e);
		}
	}
	@Override
	public void writeStream(OutputStream out, byte[] message) {
		try {
			out.write(writeByteArray(message));
		} catch (IOException e) {
			throw new AdapterException("Socket Disconnected, cannot write message",e);
		}
	}
	@Override
	public boolean isAdaptable(byte[] in) {
		return in != null && in.length > 0 && in[0] == (byte)2 && in[in.length-1] == (byte)3;
	}

	@Override
	public byte[] readByteArray(byte[] in) {
		byte[] message = new byte[in.length-2];
		System.arraycopy(in, 1, message, 0, message.length);
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
