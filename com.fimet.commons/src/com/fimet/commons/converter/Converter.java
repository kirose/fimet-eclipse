package com.fimet.commons.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fimet.commons.exception.ConverterException;

/**
 * 
 * @author <a href="mailto:marcoasb99@ciencias.unam.mx">Marco A. Salazar</a>
 *
 */
public abstract class Converter implements IConverter {

	public static final Map<Integer, IConverter> converters = new HashMap<>();
	public static final IConverter NONE               = new ConverterNone            (0,"None");
	public static final IConverter ASCII_TO_HEX       = new ConverterAsciiToHex      (1,"Ascii to Hex");
	public static final IConverter ASCII_TO_BINARY    = new ConverterAsciiToBinary   (2,"Ascii to Binary");
	public static final IConverter ASCII_TO_EBCDIC    = new ConverterAsciiToEbcdic   (3,"Ascii to Ebcdic");
	public static final IConverter EBCDIC_TO_ASCII    = new ConverterEbcdicToAscii   (4,"Ebcdic to Ascii");
	public static final IConverter EBCDIC_TO_HEX      = new ConverterEbcdicToHex     (5,"Ebcdic to Hex");
	public static final IConverter EBCDIC_TO_BINARY   = new ConverterEbcdicToBinary  (6,"EbcdicToBinary");
	public static final IConverter HEX_TO_ASCII       = new ConverterHexToAscii      (7,"Hex to Ascii");
	public static final IConverter HEX_TO_BINARY      = new ConverterHexToBinary     (8,"Hex to Binary");
	public static final IConverter HEX_TO_EBCDIC      = new ConverterHexToEbcdic     (9,"Hex to Ebcdic");
	public static final IConverter HEXEBCDIC_TO_ASCII = new ConverterHexEbcdicToAscii(10,"Hex Ebcdic to Ascii");
	public static final IConverter BINARY_TO_ASCII    = new ConverterBinaryToAscii   (11,"Binary to Ascii");
	public static final IConverter BINARY_TO_HEX      = new ConverterBinaryToHex     (12,"Binary to Hex");
	public static final IConverter BINARY_TO_EBCDIC   = new ConverterBinaryToEbcdic  (13,"Binary to Ebcdic");
	
	
	public static IConverter get(int id) {
		return converters.get(id);
	}
	public static List<IConverter> getConverters() {
		List<IConverter> list = new ArrayList<>(converters.size());
		for (Map.Entry<Integer, IConverter> c : converters.entrySet()) {
			list.add(c.getValue());
		}
		return list;
	}
	private int id;
	private String name;
	public int getId() {
		return id;
	}
	Converter(int id, String name) {
		this.id = id;
	    this.name = name;
	    converters.put(id, this);
	}
	@Override
	public String toString() {
	    return name;
	}
	
	private static final short[] asciiToEbcdicTable = { 0, 1, 2, 3, 55, 45, 46, 47, 22, 5, 37, 11, 12, 13, 14, 15, 16, 17, 18, 19, 60, 61, 50, 38, 24, 25, 63, 39, 34, 29, 53, 31, 64, 90, 127, 123, 91, 108, 80, 125, 77, 93, 92, 78, 107, 96, 75, 97, 240, 241, 242, 243, 244, 245, 246, 247, 248, 249, 122, 94, 76, 126, 110, 111, 124, 193, 194, 195, 196, 197, 198, 199, 200, 201, 209, 210, 211, 212, 213, 214, 215, 216, 217, 226, 227, 228, 229, 230, 231, 232, 233, 173, 224, 189, 95, 109, 121, 129, 130, 131, 132, 133, 134, 135, 136, 137, 145, 146, 147, 148, 149, 150, 151, 152, 153, 162, 163, 164, 165, 166, 167, 168, 169, 139, 106, 155, 161, 7 };
	private static final byte[][] asciiToHexTable_0_TO_127 = {{48,48},{48,49},{48,50},{48,51},{48,52},{48,53},{48,54},{48,55},{48,56},{48,57},{48,65},{48,66},{48,67},{48,68},{48,69},{48,70},{49,48},{49,49},{49,50},{49,51},{49,52},{49,53},{49,54},{49,55},{49,56},{49,57},{49,65},{49,66},{49,67},{49,68},{49,69},{49,70},{50,48},{50,49},{50,50},{50,51},{50,52},{50,53},{50,54},{50,55},{50,56},{50,57},{50,65},{50,66},{50,67},{50,68},{50,69},{50,70},{51,48},{51,49},{51,50},{51,51},{51,52},{51,53},{51,54},{51,55},{51,56},{51,57},{51,65},{51,66},{51,67},{51,68},{51,69},{51,70},{52,48},{52,49},{52,50},{52,51},{52,52},{52,53},{52,54},{52,55},{52,56},{52,57},{52,65},{52,66},{52,67},{52,68},{52,69},{52,70},{53,48},{53,49},{53,50},{53,51},{53,52},{53,53},{53,54},{53,55},{53,56},{53,57},{53,65},{53,66},{53,67},{53,68},{53,69},{53,70},{54,48},{54,49},{54,50},{54,51},{54,52},{54,53},{54,54},{54,55},{54,56},{54,57},{54,65},{54,66},{54,67},{54,68},{54,69},{54,70},{55,48},{55,49},{55,50},{55,51},{55,52},{55,53},{55,54},{55,55},{55,56},{55,57},{55,65},{55,66},{55,67},{55,68},{55,69},{55,70}};
	private static final byte[][] asciiToHexTable_0_TO_N128 = {{48,48},{56,48},{56,49},{56,50},{56,51},{56,52},{56,53},{56,54},{56,55},{56,56},{56,57},{56,65},{56,66},{56,67},{56,68},{56,69},{56,70},{57,48},{57,49},{57,50},{57,51},{57,52},{57,53},{57,54},{57,55},{57,56},{57,57},{57,65},{57,66},{57,67},{57,68},{57,69},{57,70},{65,48},{65,49},{65,50},{65,51},{65,52},{65,53},{65,54},{65,55},{65,56},{65,57},{65,65},{65,66},{65,67},{65,68},{65,69},{65,70},{66,48},{66,49},{66,50},{66,51},{66,52},{66,53},{66,54},{66,55},{66,56},{66,57},{66,65},{66,66},{66,67},{66,68},{66,69},{66,70},{67,48},{67,49},{67,50},{67,51},{67,52},{67,53},{67,54},{67,55},{67,56},{67,57},{67,65},{67,66},{67,67},{67,68},{67,69},{67,70},{68,48},{68,49},{68,50},{68,51},{68,52},{68,53},{68,54},{68,55},{68,56},{68,57},{68,65},{68,66},{68,67},{68,68},{68,69},{68,70},{69,48},{69,49},{69,50},{69,51},{69,52},{69,53},{69,54},{69,55},{69,56},{69,57},{69,65},{69,66},{69,67},{69,68},{69,69},{69,70},{70,48},{70,49},{70,50},{70,51},{70,52},{70,53},{70,54},{70,55},{70,56},{70,57},{70,65},{70,66},{70,67},{70,68},{70,69},{70,70}};
	private static final short[] ebcdicToAsciiTable = { 0, 1, 2, 3, 127, 9, 127, 127, 127, 127, 127, 11, 12, 13, 14, 15, 16, 17, 18, 19, 127, 127, 8, 127, 24, 25, 127, 127, 127, 29, 127, 31, 127, 127, 28, 127, 127, 10, 23, 27, 127, 127, 127, 127, 127, 5, 6, 7, 127, 127, 22, 127, 127, 30, 127, 4, 127, 127, 127, 127, 20, 21, 127, 26, 32, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 46, 60, 40, 43, 127, 38, 127, 127, 127, 127, 127, 127, 127, 127, 127, 33, 36, 42, 41, 59, 94, 45, 47, 127, 127, 127, 127, 127, 127, 127, 127, 124, 44, 37, 95, 62, 63, 127, 127, 127, 127, 127, 127, 127, 127, 127, 96, 58, 35, 64, 39, 61, 34, 127, 97, 98, 99, 100, 101, 102, 103, 104, 105, 127, 123, 127, 127, 127, 127, 127, 106, 107, 108, 109, 110, 111, 112, 113, 114, 127, 125, 127, 127, 127, 127, 127, 126, 115, 116, 117, 118, 119, 120, 121, 122, 127, 127, 127, 91, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 93, 127, 127, 127, 65, 66, 67, 68, 69, 70, 71, 72, 73, 127, 127, 127, 127, 127, 127, 127, 74, 75, 76, 77, 78, 79, 80, 81, 82, 127, 127, 127, 127, 127, 127, 92, 127, 83, 84, 85, 86, 87, 88, 89, 90, 127, 127, 127, 127, 127, 127, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 127, 127, 127, 127, 127, 127 };
	private static final byte ZERO_ASCII = 48;
	private static final byte ONE_ASCII = 49;
	
	public static byte[] asciiToBinary(byte[] ascii) {
		byte[] binary = new byte[ascii.length*8];
		int index;
		byte b;
		for (int i = 0; i < ascii.length; i++) {
			b = ascii[i];
			index = i*8;
			binary[index+7] =   (1 & b) > 0 ? ONE_ASCII : ZERO_ASCII;
			binary[index+6] =   (2 & b) > 0 ? ONE_ASCII : ZERO_ASCII;
			binary[index+5] =   (4 & b) > 0 ? ONE_ASCII : ZERO_ASCII;
			binary[index+4] =   (8 & b) > 0 ? ONE_ASCII : ZERO_ASCII;
			binary[index+3] =  (16 & b) > 0 ? ONE_ASCII : ZERO_ASCII;
			binary[index+2] =  (32 & b) > 0 ? ONE_ASCII : ZERO_ASCII;
			binary[index+1] =  (64 & b) > 0 ? ONE_ASCII : ZERO_ASCII;
			binary[index]   = (128 & b) > 0 ? ONE_ASCII : ZERO_ASCII;
		}
		return binary;
	}
	public static byte[] asciiToEbcdic(byte[] ascii) {
		byte[] ebcdic = new byte[ascii.length];
		for (int i = 0; i < ebcdic.length; i++) {
			ebcdic[i] = (byte) asciiToEbcdicTable[ascii[i] % 128];
		}
		return ebcdic;
	}
	public static byte[] asciiToHex(byte ascii) {
		return ascii >= 0 ? asciiToHexTable_0_TO_127[ascii] : asciiToHexTable_0_TO_N128[129+ascii];
	}
	public static byte[] asciiToHex(byte[] ascii) {
		byte[] hex = new byte[ascii.length*2];
		byte[] pair;
		for (int i = 0; i < ascii.length; i++) {
			pair = ascii[i] >= 0 ? asciiToHexTable_0_TO_127[ascii[i]] : asciiToHexTable_0_TO_N128[129+ascii[i]];
			hex[i*2] = pair[0];
			hex[i*2+1] = pair[1];
		}
		return hex;
	}
	public static byte[] ebcdicToAscii(byte[] ebcdic) {
		byte[] ascii = new byte[ebcdic.length];
		for (int i = 0; i < ebcdic.length; i++) {
			ascii[i] = (byte) ebcdicToAsciiTable[0xFF & ebcdic[i]];
		}
		return ascii;
	}
	public static byte[] ebcdicToBinary(byte[] ebcdic) {
		byte[] binary = new byte[ebcdic.length*8];
		int index;
		byte b;
		for (int i = 0; i < ebcdic.length; i++) {
			b = ebcdic[i];
			index = i*8;
			binary[index+7] =   (1 & b) > 0 ? ONE_ASCII : ZERO_ASCII;
			binary[index+6] =   (2 & b) > 0 ? ONE_ASCII : ZERO_ASCII;
			binary[index+5] =   (4 & b) > 0 ? ONE_ASCII : ZERO_ASCII;
			binary[index+4] =   (8 & b) > 0 ? ONE_ASCII : ZERO_ASCII;
			binary[index+3] =  (16 & b) > 0 ? ONE_ASCII : ZERO_ASCII;
			binary[index+2] =  (32 & b) > 0 ? ONE_ASCII : ZERO_ASCII;
			binary[index+1] =  (64 & b) > 0 ? ONE_ASCII : ZERO_ASCII;
			binary[index]   = (128 & b) > 0 ? ONE_ASCII : ZERO_ASCII;
		}
		return binary;
	}
	public static byte[] ebcdicToHex(byte[] ebcdic) {
		byte[] hex = new byte[ebcdic.length*2];
		byte[] pair;
		for (int i = 0; i < ebcdic.length; i++) {
			pair = ebcdic[i] >= 0 ? asciiToHexTable_0_TO_127[ebcdic[i]] : asciiToHexTable_0_TO_N128[129+ebcdic[i]];
			hex[i*2] = pair[0];
			hex[i*2+1] = pair[1];
		}
		return hex;
	}
	public static byte[] hexToAscii(byte[] hex) {
		int ln = hex.length/2;
		byte[] ascii = new byte[ln];
		for (int i = 0; i < ln; i++) {
			ascii[i] = (byte)(removeOffset(hex[i*2]) << 4 | removeOffset(hex[i*2+1]));
		}
		return ascii;
	}
	public static byte[] hexToBinary(byte[] hex) {
		byte[] binary = new byte[hex.length*4];
		int index;
		byte b;
		for (int i = 0; i < hex.length; i++) {
			b = removeOffset(hex[i]);
			index = i*4;
			binary[index+3] = (1 & b) > 0 ? ONE_ASCII : ZERO_ASCII;
			binary[index+2] = (2 & b) > 0 ? ONE_ASCII : ZERO_ASCII;
			binary[index+1] = (4 & b) > 0 ? ONE_ASCII : ZERO_ASCII;
			binary[index] =   (8 & b) > 0 ? ONE_ASCII : ZERO_ASCII;
		}
		return binary;
	}
	public static byte[] hexToEbcdic(byte[] hex) {
		int ln = hex.length/2;
		byte[] ascii = new byte[ln];
		for (int i = 0; i < ln; i++) {
			ascii[i] = (byte) asciiToEbcdicTable[(removeOffset(hex[i*2]) << 4 | removeOffset(hex[i*2+1])) % 128];
		}
		return ascii;
	}
	public static byte[] binaryToHex(byte[] binary) {
		int ln = binary.length/4;
		byte[] hex = new byte[ln];
		int index;
		for (int i = 0; i < ln; i++) {
			index = i*4;
			hex[i] = addOffset((byte)(
				(binary[index+3] == ZERO_ASCII ? 0 : 1)|
				(binary[index+2] == ZERO_ASCII ? 0 : 2)|
				(binary[index+1] == ZERO_ASCII ? 0 : 4)|
				(binary[index]   == ZERO_ASCII ? 0 : 8)
			));
		}
		return hex;
	}
	public static byte[] binaryToAscii(byte[] binary) {
		int ln = binary.length/8;
		byte[] ascii = new byte[ln];
		int index;
		for (int i = 0; i < ln; i++) {
			index = i*8;
			ascii[i] = (byte)(
				(binary[index+7] == ZERO_ASCII ? 0 : 1)|
				(binary[index+6] == ZERO_ASCII ? 0 : 2)|
				(binary[index+5] == ZERO_ASCII ? 0 : 4)|
				(binary[index+4] == ZERO_ASCII ? 0 : 8)|
				(binary[index+3] == ZERO_ASCII ? 0 : 16)|
				(binary[index+2] == ZERO_ASCII ? 0 : 32)|
				(binary[index+1] == ZERO_ASCII ? 0 : 64)|
				(binary[index]   == ZERO_ASCII ? 0 : 128)
			);
		}
		return ascii;
	}
	public static byte[] binaryToEbcdic(byte[] binary) {
		int ln = binary.length/8;
		byte[] ebcdic = new byte[ln];
		int index;
		for (int i = 0; i < ln; i++) {
			index = i*8;
			ebcdic[i] = (byte)(
				(binary[index+7] == ZERO_ASCII ? 0 : 1)|
				(binary[index+6] == ZERO_ASCII ? 0 : 2)|
				(binary[index+5] == ZERO_ASCII ? 0 : 4)|
				(binary[index+4] == ZERO_ASCII ? 0 : 8)|
				(binary[index+3] == ZERO_ASCII ? 0 : 16)|
				(binary[index+2] == ZERO_ASCII ? 0 : 32)|
				(binary[index+1] == ZERO_ASCII ? 0 : 64)|
				(binary[index]   == ZERO_ASCII ? 0 : 128)
			);
		}
		return ebcdic;
	}
	/**
	 * 
	 * ASCIIINT ASCII  removeOffset
 	 *    48      0       0
 	 *    49      1       1
 	 *    50      2       2
 	 *    51      3       3
 	 *    52      4       4
 	 *    53      5       5
 	 *    54      6       6
 	 *    55      7       7
 	 *    56      8       8
 	 *    57      9       9
 	 *    65      A      10
 	 *    66      B      11
 	 *    67      C      12
 	 *    68      D      13
 	 *    69      E      14
 	 *    70      F      15
	 * @param b
	 * @return
	 */
	static byte removeOffset(byte b) {
		if (b >= 48 && b <= 57) {// b >= '0' && b <= '9'
			return (byte)(b-48);
		}
		if (b >= 65 && b <= 70) {//b >= 'A' b <= 'F'
			return (byte)(b-55);
		}
		if (b >= 97 && b <= 102) {//b >= 'a' b <= 'f'
			return (byte)(b-87);
		}
		throw new ConverterException("Byte '"+((char)b)+"' ("+b+") is not hex");
	}
	static byte addOffset(byte b) {
		if (b >= 0 && b <= 9) {// b >= '0' && b <= '9'
			return (byte)(b+48);
		}
		if (b >= 10 && b <= 15) {//b >= 'A' b <= 'F'
			return (byte)(b+55);
		}
		throw new ConverterException("Byte '"+((char)b)+"' ("+b+") is not hex");
	}
}