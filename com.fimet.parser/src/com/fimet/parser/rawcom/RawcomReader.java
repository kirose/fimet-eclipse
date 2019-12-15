package com.fimet.parser.rawcom;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import com.fimet.commons.exception.RawcomException;
import com.fimet.commons.utils.ByteUtils;
/**
 * 
 * @author <a href="mailto:marcoasb99@ciencias.unam.mx">Marco A. Salazar</a>
 *
 */
public class RawcomReader implements Iterable<Rawcom> {
	private static final int MAX_READ = 512;
	private File file;
	private RawcomIterator iterator;
	public RawcomReader(File file) throws FileNotFoundException {
		this.file = file;
		iterator = new RawcomIterator();
	}
	public void readRawcom(File in) {
		
	}
	public Iterator<Rawcom> iterator() {
		return iterator;
	}
	public void close() {
		if (iterator != null && iterator.reader != null) {
			try {
				iterator.reader.close();
			} catch (Exception e) {}
			iterator.reader = null;
			iterator = null;
		}
	}
	private class RawcomIterator implements Iterator<Rawcom>{
		FileInputStream reader = null;
		byte[] buffer;
		int index;
		int ln;
		Rawcom next;
		private RawcomIterator() throws FileNotFoundException {
			reader = new FileInputStream(file);
		}
		private boolean resizeAndRead() throws IOException {
			if (buffer == null) {
				buffer = new byte[MAX_READ];	
				ln = reader.read(buffer, 0, buffer.length);
				return ln > 0;
			} else if (reader != null) {
				byte[] bytes = new byte[buffer.length*2];
				System.arraycopy(buffer, 0, bytes, 0, ln);
				int length = reader.read(bytes, ln, buffer.length*2-ln);
				buffer = bytes;
				if (length > 0) {
					ln += length;
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
		private boolean hasNextByte(int index) throws IOException {
			return index < ln || resizeAndRead();
		}
		private boolean hasNextByte() throws IOException {
			return index < ln || resizeAndRead();
		}
		public boolean hasNext() {
			return next != null || (reader != null && buildNextMessage());
		}

		public Rawcom next() {
			if (hasNext()) {
				Rawcom out = next;
				next = null;
				return out;
			} else {
				return null;
			}
		}
		private boolean buildNextMessage() {
			try {

				while(nextIsBlank()) {index++;}
				int start = index;
				int startMessage = -1;
				int end = -1;
				index += assertStartRawcom(index);

				while (hasNextByte(index)) {
					if (//[L:  308]
						buffer[index] == (byte)91 && //[ 
						is(index+1, (byte)76) && //L
						is(index+2, (byte)58)//:
					) {
						index += 3;
						while(nextIsBlank()) {index++;}
						while(isNumeric(index)) {index++;}
						assertValue(index++, (byte)93);//]
						startMessage = index;
						break;
					} else {
						index++;
					}
				}
				int attempt = index;
				while (hasNextByte(attempt)) {
					if (
						buffer[attempt] == (byte)91 && //[ 
						is(attempt+1, (byte)84) && //T
						is(attempt+2, (byte)58)//:
					) {
						end = attempt;
						attempt += 3;
						while(isBlank(attempt)) {attempt++;}
						if (
							isNumeric(attempt++) && //0-9
							isNumeric(attempt++) && //0-9
							is(attempt++, (byte)58) && //:
							isNumeric(attempt++) && //0-9
							isNumeric(attempt++) && //0-9
							is(attempt++, (byte)58) && //:
							isNumeric(attempt++) && //0-9
							isNumeric(attempt++) && //0-9
							is(attempt++, (byte)46) //.
						) {
							break;
						} else {
							end = -1;
						}
					}
					attempt++;
				}
				if (!hasNextByte(attempt)) {
					end = attempt;
					try {
						reader.close();
					} catch (Exception e) {}
					reader = null;
				}
				if (end == -1 || startMessage == -1) {
					return false;
				}
				byte[] bytes = new byte[end-start];
				System.arraycopy(buffer, start, bytes, 0, end-start);
				next = new Rawcom(bytes, startMessage);
				clearBuffer(end);
				return true;
			
			} catch (IOException e) {
				e.printStackTrace();
				throw new RawcomException("Rawcom error",e);
			}
		}
		private void clearBuffer(int index) {
			for (int i = index; i < buffer.length; i++) {
				buffer[i-index] = buffer[i];
			}
			ln = ln - index;
			this.index = 0;
		}
		private int assertStartRawcom(int index) throws IOException {
			int i = index;
			if (!(
					is(i++, (byte)91)//[
				&& is(i++, (byte)84)//T
				&& is(i++, (byte)58))//:
			) {
				throw new RawcomException("Expected pattern '[T:' instead found "+new String(ByteUtils.subArray(buffer, index, index + i))+" ");
			}
			while(nextIsBlank()) {i++;}
			if (!(
				isNumeric(i++)//0-9
				&& isNumeric(i++)//0-9
				&& is(i++, (byte)58)//:
				&& isNumeric(i++)//0-9
				&& isNumeric(i++)//0-9
				&& is(i++, (byte)58)//:
				&& isNumeric(i++)//0-9
				&& isNumeric(i++)//0-9
				&& is(i++, (byte)46)//.
				&& isNumeric(i++)//0-9
			)) {
				throw new RawcomException("Expected pattern '[T: 99:99:99.9' instead found "+new String(ByteUtils.subArray(buffer, index, index + i))+" ");
			}
			return i;
		}
		private void assertValue(int index, byte b) {
			if (b != buffer[index]) {
				throw new RawcomException("Expected byte "+b+" ("+((char)b)+") instead found "+buffer[index]+" ("+((char)buffer[index])+")");
			}
		}
		private boolean is(int index, byte b) throws IOException {
			return hasNextByte(index) && b == buffer[index];
		}
		private boolean isNumeric(int index) throws IOException {
			return hasNextByte(index) && !(buffer[index] < (byte)48 || buffer[index] > 57);//bytes[index] < '0' || bytes[index] > '9'
		}
		private boolean isBlank(int index) throws IOException {
			if (hasNextByte()) {
				byte b = buffer[index];
				if (b == (byte)9 || b == (byte)32 || b == (byte)10) {//b == \t || b == ' ' || b == '\n' 
					return true;
				} else if (b == (byte)13) {//\r
					if (index+1 < ln || resizeAndRead()) {
						if (buffer[index+1] == (byte)10) {//\n
							return true;
						}
					}
				}
			}
			return false;
		}
		private boolean nextIsBlank() throws IOException {
			if (hasNextByte()) {
				byte b = buffer[index];
				if (b == (byte)9 || b == (byte)32 || b == (byte)10) {//b == \t || b == ' ' || b == '\n' 
					return true;
				} else if (b == (byte)13) {//\r
					if (index+1 < ln || resizeAndRead()) {
						if (buffer[index+1] == (byte)10) {//\n
							return true;
						}
					}
				}
			}
			return false;
		}
	}
}
