package com.fimet.commons.data.writer.impl;

import com.fimet.commons.data.writer.IWriter;

public class ByteArrayWriter implements IWriter {

	private static final int DEFAULT_SIZE = 512; 
	private byte[] bytes;
	private int cursor;
	public ByteArrayWriter() {
		this(DEFAULT_SIZE);
	}
	public ByteArrayWriter(int initialCapacity) {
		assert(initialCapacity > 0);
		bytes = new byte[initialCapacity];
	}
	public void move(int offset) {
		cursor += offset;
	}
	public int length() {
		return cursor;
	}
	public void append(String text) {
		append(text.getBytes());
	}
	public void append(byte[] bytes) {
		if (bytes == null) {
			throw new NullPointerException();
		}
		if (bytes.length > this.bytes.length-cursor) {
			expand(bytes.length);
		}
		System.arraycopy(bytes, 0, this.bytes, cursor, bytes.length);
		cursor += bytes.length;
	}
	public void append(byte b) {
		if (1 > this.bytes.length-cursor) {
			expand(1);
		}
		this.bytes[cursor++] = b;
	}
	public void preappend(String text) {
		preappend(text.getBytes());
	}
	public void preappend(byte[] bytes) {
		if (bytes == null) {
			throw new NullPointerException();
		}
		if (bytes.length > this.bytes.length-cursor) {
			expand(bytes.length);
		}
		moveBytes(0,bytes.length);
		System.arraycopy(bytes, 0, this.bytes, 0, bytes.length);
	}
	public void insert(int cursor, String text) {
		insert(cursor, text.getBytes());
	}
	public void insert(int cursor, byte[] bytes) {
		if (cursor == 0) {
			preappend(bytes);
			return;
		}
		if (cursor == this.cursor) {
			append(bytes);
			return;
		}
		if (bytes == null) {
			throw new NullPointerException();
		}
		if (cursor < 0 || cursor > this.cursor) {
			throw new IndexOutOfBoundsException();
		}
		if (bytes.length > this.bytes.length-cursor) {
			expand(bytes.length);
		}
		moveBytes(cursor,bytes.length);
		System.arraycopy(bytes, 0, this.bytes, cursor, bytes.length);
	}
	public void replace(int cursor, String text) {
		replace(cursor, text.getBytes());
	}
	public void replace(int cursor, byte[] bytes) {
		if (bytes == null) {
			throw new NullPointerException();
		}
		if (cursor < 0 || cursor > this.cursor) {
			throw new IndexOutOfBoundsException();
		}
		if (bytes.length > this.bytes.length-cursor) {
			expand(bytes.length);
		}
		System.arraycopy(bytes, 0, this.bytes, cursor, bytes.length);
	}
	private void moveBytes(int start, int offset) {
		int ln = start - cursor;
		cursor += offset;
		for (int i = cursor - 1; i < start; i--) {
			bytes[i] = bytes[i-ln];
		}
	}
	public byte[] getBytes() {
		byte[] copy = new byte[cursor];
		System.arraycopy(bytes, 0, copy, 0, copy.length);
		return copy;
	}
	public String toString() {
		return new String(bytes, 0, cursor);
	}
	private void expand(int length) {
		byte[] newBytes = new byte[length > bytes.length*2 ? length :  bytes.length*2];
		System.arraycopy(bytes, 0, newBytes, 0, bytes.length);
		this.bytes = newBytes;
	}
}
