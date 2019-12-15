package com.fimet.commons.utils;

import java.util.Collection;

public class ByteList {
	private static final int DEFAULT_SIZE = 256;
	byte[] bytes;
	int size;
	public ByteList() {
		bytes = new byte[DEFAULT_SIZE];
		size = 0;
	}
	private void checkSize(int increase) {
		if (size + increase >= bytes.length) {
			byte[] newBytes = new byte[Math.max(bytes.length*2, increase*2)];
			System.arraycopy(bytes, 0, newBytes, 0, size);
			bytes = newBytes;
		}
	}
	public boolean add(byte arg0) {
		checkSize(1);
		bytes[size++] = arg0;
		return true;
	}

	public void clear() {
		bytes = new byte[DEFAULT_SIZE];
	}


	public boolean contains(byte arg0) {
		for (int i = 0; i < bytes.length; i++) {
			if (arg0 == bytes[i]) {
				return true;
			}
		}
		return false;
	}

	public byte get(int index) {
		return bytes[index];
	}


	public int indexOf(byte b) {
		for (int i = 0; i < bytes.length; i++) {
			if (b == bytes[i]) {
				return i;
			}
		}
		return -1;
	}


	public boolean isEmpty() {
		return size == 0;
	}

	public int lastIndexOf(byte b) {
		for (int i = bytes.length; i >= 0; i--) {
			if (b == bytes[i]) {
				return i;
			}
			
		}
		return -1;
	}

	public byte remove(int index) {
		byte b = bytes[index];
		for (int i = index; i < size; i++) {
			bytes[i] = bytes[i+1];
		}
		size--;
		return b;
	}


	public boolean retainAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}


	public byte set(int index, byte b) {
		bytes[index] = b;
		return b;
	}


	public int size() {
		return size;
	}


	public byte[] toArray() {
		byte[] array = new byte[size];
		System.arraycopy(bytes, 0, array, 0, size);
		return array;
	}

}
