package com.fimet.commons.data.reader.impl;

import java.math.BigInteger;

import com.fimet.commons.data.reader.IMatcher;
import com.fimet.commons.exception.FimetException;
import com.fimet.commons.utils.StringUtils;

public class StringReader {//implements IReader {
	private String src;
	private int cursor;
	private int length;
	
	public StringReader(String src){
		if (src == null) {
			throw new NullPointerException();
		}
		this.src = src;
		this.cursor = 0;
		this.length = src.length();
	}

	/*public IChunk chunk(int length) {
		MsgChunk chunk = new MsgChunk(this, cursor, length);
		cursor += length;
		return chunk;
	}*/
	public IMatcher matcher(String text) {
		return new MsgMatcher(this, text);
	}
	public void assertChar(char c) {
		if (src.charAt(cursor) != c) {
			throw new FimetException("Expected char '"+c+"' found: "+src.charAt(cursor));
		}
		cursor++;
	}
	public String toString() {
		return src.substring(cursor);
	}
	public int getLength() {
		return length;
	}
	public boolean startsWith(String prefix) {
		return src.startsWith(prefix);
	}
	public boolean hasNext() {
		return cursor < length;
	}
	public void move(int no) {
		if (cursor + no > length || cursor + no < 0) {
			throw new IndexOutOfBoundsException();
		}
		cursor += no;
	}
	public static class MsgMatcher implements IMatcher {
		private StringReader msgBuffered;
		private String pattern;
		private int start;
		private int end;
		private MsgMatcher(StringReader buf, String pattern) {
			if (pattern == null) {
				throw new NullPointerException();
			}
			this.msgBuffered = buf;
			this.start = buf.cursor;
			this.end = start + pattern.length(); 
			this.pattern = pattern;
			if (start < 0) {
				throw new IndexOutOfBoundsException();
			}
			if (end <= 0) {
				throw new IndexOutOfBoundsException();
			}
			if (end > buf.length) {
				throw new IndexOutOfBoundsException();
			}
		}
		public boolean asChar() {
			for (int i = start; i < end; i++) {
				if (msgBuffered.src.charAt(i) != pattern.charAt(i-start)) {
					return false;
				}
			}
			return true;
		}
		public boolean asByte() {
			for (int i = start; i < end; i++) {
				if (msgBuffered.src.charAt(i) != pattern.charAt(i-start)) {
					return false;
				}
			}
			return true;
		}
	}

	/**
	 * Represents a chunk of Message
	 * This class contains useful methods to convert the chunk into some representation:
	 * -hexadecimal
	 * -bin
	 * -ascci
	 * 
	 * @author Marco Antonio
	 *
	 */
	public static class MsgChunk {//implements IChunk
		private StringReader msgBuffered;
		private int start;
		private int end;
		private MsgChunk(StringReader buf, int start, int length) {
			this.start = start;
			this.end = start + length;
			this.msgBuffered = buf;
			if (start < 0) {
				throw new IndexOutOfBoundsException();
			}
			if (length <= 0) {
				throw new IndexOutOfBoundsException();
			}
			if (start + length > buf.length) {
				throw new IndexOutOfBoundsException();
			}
		}
		/**
		 * 	new byte[]{48,54,56,55} -> 687
		 * @return int
		 */
		public int toInt() {
			return Integer.parseInt(msgBuffered.src.substring(start,end));
		}
		/**
		 * new byte[]{7} -> 7
		 * new byte[]{E} -> ERROR
		 * @return int
		 */
		public int toIntAsHex() {
			StringBuilder sb = new StringBuilder();
			for (int i = start; i < end; i++) {
				sb.append(String.format("%02X", msgBuffered.src.charAt(i)));
			}
			return Integer.parseInt(sb.toString());
		}
		/**
		 * {72,111,108,97} -> "Hola"
		 * @return String representation
		 */
		public String toString() {
			return msgBuffered.src.substring(start,end);
		}
		/**
		 * {72,111,108,97} -> "Hola"
		 * @return String in Hexadecimal representation
		 */
		public String toHexString() {
			StringBuilder sb = new StringBuilder();
			for(int i=start; i < end; i++){
				sb.append(String.format("%02X", msgBuffered.src.charAt(i)));
			}
			return sb.toString();
		}
		/**
		 * 
		 * @return String in Bit representation
		 */
		public String toBitString() {
			StringBuilder sb = new StringBuilder();
			for(int i=start; i < end; i++){
				sb.append(StringUtils.leftPad(Integer.toBinaryString((int)msgBuffered.src.charAt(i)), 8, '0'));
			}
			return sb.toString();
		}
		/**
		 * 
		 * @return String in Bit representation
		 */
		public String toBitStringAsHex() {
			StringBuilder sb = new StringBuilder();
			for(int i=start; i < end; i++){
				sb.append(StringUtils.leftPad(Integer.toBinaryString(Integer.parseInt(""+((char)msgBuffered.src.charAt(i)),16)), 4, '0'));
			}
			return sb.toString();
		}
		/**
		 * 
		 * @return String ascii
		 */
		public String toAsciiAsEbcdic() {
			byte[] ascii = new byte[end-start];
			for (int i = start; i < end; i++) {
				//int ebcdicIndex = 0xFF & msgBuffered.src.charAt(i);
				//ascii[i] = (byte) ebcdicToAsciiTable[ebcdicIndex];
			}
			return new String(ascii);
		}
		/**
		 * new byte[]{49,69} -> 1E ->30 
		 * @return int
		 */
		public int hexToInt() {
			StringBuilder sb = new StringBuilder();
			for (int i = start; i < end; i++) {
				sb.append(String.format("%02X", msgBuffered.src.charAt(i)));
			}
			return Integer.parseInt(sb.toString(),16);
		}
		/**
		 * new byte[]{49,69} -> 1E ->30 
		 * @return int
		 */
		public String hexToBits() {
			StringBuilder sb = new StringBuilder();
			for(int i=start; i < end; i++){
				sb.append(String.format("%02X", msgBuffered.src.charAt(i)));
			}
			return StringUtils.leftPad(new BigInteger(sb.toString(),16).toString(2),(end-start)*8,'0');
		}
	}

}
