package com.fimet.commons.utils;

import com.fimet.commons.exception.FimetException;

public final class ByteUtils {

	private ByteUtils() {}
	public static byte[] concat(byte[] ...bs) {
		if (bs == null || bs.length == 0) {
			return null;
		}
		if (bs.length == 1) {
			return bs[0];
		}
		int ln = 0;
		for (byte[] b : bs) {
			ln += b.length;
		}
		byte[] cat = new byte[ln];
		int i = 0;
		for (byte[] b : bs) {
			System.arraycopy(b, 0, cat, i, b.length);
			i+=b.length;
		}
		return cat;
	}
	public static byte[] concat(byte b1[], byte b2[]) {
		if (b1 == null)
			return b2;
		if (b2 == null)
			return b1;
		byte[] cat = new byte[b1.length+b2.length];
		System.arraycopy(b1, 0, cat, 0, b1.length);
		System.arraycopy(b2, 0, cat, b1.length, b2.length);
		return cat;
	}
	public static String arrayToHex(byte byteArray[]) {
		StringBuilder byteString = new StringBuilder();
		for (int i = 0; i < byteArray.length; i++) {
			byteString.append(String.format("%02X",byteArray[i]));
		}
		return byteString.toString();
	}
	public static byte[] append(byte[] bytes, byte b) {
		byte[] newBytes = new byte[bytes.length+1];
		System.arraycopy(bytes, 0, newBytes, 0, bytes.length);
		newBytes[bytes.length] = b;
		return newBytes;
	}
	public static byte[] preappend(byte[] bytes, byte b) {
		byte[] newBytes = new byte[bytes.length+1];
		System.arraycopy(bytes, 0, newBytes, 1, bytes.length);
		newBytes[0] = b;
		return newBytes;
	}
	public static byte[] hexToAscii(String hex) {
		if (hex == null) {
			return null;
		}
		byte[] bytes = new byte[hex.length()/2];
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseUnsignedInt(hex.substring(2*i, 2*(i+1)), 16);
		}
		return bytes;
	}
	public static byte[] removeEndNewLines(byte[] bytes) {
		int ln = bytes.length;
		while (ln > 0 && bytes[ln-1] == (byte)10) {
			ln = ln -1;
			if (bytes[ln-1] == (byte)13) {//'\r') {
				ln = ln -1;
			}
		}
		if (ln != bytes.length) {
			byte[] newBytes = new byte[ln];
			System.arraycopy(bytes, 0, newBytes, 0, ln);
			return newBytes;
		} else {
			return bytes;
		}
	}
	public static void assertNumeric(byte[] bytes, int index) {
		if (bytes == null || index >= bytes.length) {
			throw new FimetException("Byte not consummed at position "+index+", Expected Numeric");
		}
		if (bytes[index] < (byte)48 || bytes[index] > 57) {//bytes[index] < '0' || bytes[index] > '9'
			throw new FimetException("Expected Numeric instead found "+bytes[index]);
		}
	}
	public static void assertByte(byte[] bytes, int index, byte byteExpected) {
		if (bytes == null || index >= bytes.length) {
			throw new FimetException("Byte not consummed at position "+index+", Expected "+byteExpected+"");
		}
		if (byteExpected != bytes[index]) {
			throw new FimetException("Expected "+byteExpected+" instead found "+bytes[index]);
		}
	}
	public static int indexNextNotBlank(byte[] bytes, int start) {
		if (bytes == null || start >= bytes.length) {
			return -1;
		}
		byte b;
		int ln = bytes.length;
		while (start < ln) {
			b = bytes[start];
			if (b == (byte)9 || b == (byte)32 || b == (byte)10) {//b == \t || b == ' ' || b == '\n' 
				start++;
			} else if (b == (byte)13) {//\r
				if (start+1 < ln && bytes[start+1] == (byte)10) {//\n
					start += 2;
				} else {
					break;
				}
			} else {
				break;
			}
		}
		return start;
	}
	public static int indexNextNotNumeric(byte[] bytes, int start) {
		if (bytes == null || start >= bytes.length) {
			return -1;
		}
		byte b;
		int ln = bytes.length;
		while (start < ln) {
			b = bytes[start];
			if (b < (byte)48 || b > 57) {//b < 0 || b > 9 
				break;
			} else {
				start++;
			}
		}
		return start;
	}
	public static byte[] subArray(byte[] bytes, int start) {
		return subArray(bytes, start, bytes.length);
	}
	public static byte[] subArray(byte[] bytes, int start, int end) {
		byte[] subarray = new byte[end-start];
		System.arraycopy(bytes, start, subarray, 0, end-start);
		return subarray;
	}
	public static boolean isHex(byte b) {
		return  (b >= 48 && b <= 57) || // b >= '0' && b <= '9'
				(b >= 65 && b <= 70) || //b >= 'A' b <= 'F'
				(b >= 97 && b <= 102);  //b >= 'a' b <= 'f'
	}
	public static byte[] join(byte[] mli, byte[] iso) {
		byte[] msg = new byte[mli.length + iso.length];
		System.arraycopy(mli, 0, msg, 0, mli.length);
		System.arraycopy(iso, 0, msg, mli.length, iso.length);
		return msg;
	}
}
