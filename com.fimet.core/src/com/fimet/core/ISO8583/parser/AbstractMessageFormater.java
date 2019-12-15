package com.fimet.core.ISO8583.parser;

import com.fimet.commons.converter.Converter;
import com.fimet.commons.data.writer.IWriter;
import com.fimet.commons.data.writer.impl.ByteArrayWriter;
import com.fimet.commons.exception.FormatException;
import com.fimet.core.Activator;
import com.fimet.core.entity.sqlite.Parser;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public abstract class AbstractMessageFormater extends AbstractBaseParser {

	private static final int SIZE_BITMAP = 4*16;
	private static final String EMPTY_BITMAP = "1000000000000000000000000000000000000000000000000000000000000000";
	
	public AbstractMessageFormater(Parser entity) {
		super(entity);
	}

	@Override
	public byte[] formatMessage(IMessage message) {
		IWriter writer = new ByteArrayWriter();
		Message msg = (Message)message;
		formatHeader(writer, msg);
		formatBitmap(writer, msg);
		formatBody(writer, msg);
		byte[] iso = getConverter().deconvert(writer.getBytes());
		return iso;
	}
	protected void formatHeader(IWriter writer, Message msg) {
		if (msg.getHeader().length() != 12) {
			throw new FormatException("ISO header section invalid expected length 12 found("+msg.getHeader().length()+"): '"+msg.getHeader()+"'");
		}
		if (msg.getMti().length() != 4) {
			throw new FormatException("MTI invalid expected length 4 found('"+msg.getMti().length()+"'): '"+msg.getMti()+"'");
		}
		writer.append(msg.getHeader());
		writer.append(msg.getMti());
	}
	protected void formatBitmap(IWriter writer, Message msg) {
		String bitmap = buildBitmap(msg);
		writer.append(Converter.binaryToHex(bitmap.getBytes()));
	}
	protected String buildBitmap(Message msg) {
		int[] bitmap = msg.getBitmap();
	
		int endBitmap = bitmap == null || bitmap.length == 0 ? 1 : bitmap(bitmap[bitmap.length-1]);
		StringBuilder bitmapStr = new StringBuilder();
		for (int i = 0; i < endBitmap; i++) {
			bitmapStr.append(EMPTY_BITMAP);
		}
		bitmapStr.replace(bitmapStr.length()-64, bitmapStr.length()-63, "0");
		int bit;
		for (int index : bitmap) {
			bit = bitmap(index);
			index = index + (bit == 1 ? -1 : bit - 3);
			bitmapStr.replace(index, index+1, "1");
		}
		return bitmapStr.toString();
	}
	/**
	 * Returns the bitmap number to which index belongs
	 * @param index
	 * @return bitmap number
	 */
	protected int bitmap(int index) {
		if (index <= SIZE_BITMAP) {
			return 1;
		} else {
			return 2;
		}
		//return ((index - index%SIZE_BITMAP)/SIZE_BITMAP+1);
	}
	protected void formatBody(IWriter writer, Message msg) {
		for (int field : msg.getBitmap()) {
			try {
				getFieldParserManager().format(msg, field, writer);
			} catch (Exception e) {
				Activator.getInstance().error(this+" error formating field "+field,e);
				throw e;
			}
		}
	}
}
