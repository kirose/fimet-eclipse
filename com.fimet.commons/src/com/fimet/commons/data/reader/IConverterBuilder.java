package com.fimet.commons.data.reader;
/**
 * 
 * @author Marco Antonio
 *
 */
public interface IConverterBuilder {
	/**
	 * Return the byte[] representation of this IConverter
	 * @return
	 */
	byte[] toBytes();
	/**
	 * Return the String representation of this IConverter
	 * @return
	 */
	String toString();
	
	/**
	 * Convert a string to integer using radix = 10
	 * @return
	 */
	int toInt();
	/**
	 * Convert a string to integer using radix
	 * @return
	 */
	int toInt(int radix);
	/**
	 * Convert to hex (String)
	 * @return
	 */
	IConverterBuilder toHex();
	/**
	 * Convert to Binary (String)
	 * @return
	 */
	IConverterBuilder toBinary();
	/**
	 * Convert to Ascii (String)
	 * @return
	 */
	IConverterBuilder toAscii();
	/**
	 * Convert to Ebcdic (String)
	 * @return
	 */
	IConverterBuilder toEbcdic();
	/**
	 * Assume actual data type as Binary
	 * @return
	 */
	IConverterBuilder asBinary();
	/**
	 * Assume actual data type as Hex
	 * @return
	 */
	IConverterBuilder asHex();
	/**
	 * Assume actual data type as Ascii
	 * @return
	 */
	IConverterBuilder asAscii();
	/**
	 * Assume actual data type as Ebcdic
	 * @return
	 */
	IConverterBuilder asEbcdic();

}
