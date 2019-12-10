/**
 * 
 */
package com.fimet.core.impl.utils;

import java.math.BigInteger;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

import com.fimet.commons.converter.Converter;

/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public final class ConverterUtils {

	public static final String REGEXP_NUMERIC = "[0-9]+";
	public static final String REGEXP_HEX = "[0-9A-Fa-f]+";
	public static final String REGEXP_BIT = "[0-1]+";
	
	private ConverterUtils() {}
	public static String ebcdicToAscii(String ebcdic) {
		return new String(Converter.ebcdicToAscii(ebcdic.getBytes()));
	}
	public static String ebcdicToHex(String ebcdic) {
		return new String(Converter.ebcdicToHex(ebcdic.getBytes()));
	}
	public static String ebcdicToBinary(String ebcdic) {
		return new String(Converter.ebcdicToBinary(ebcdic.getBytes()));
	}
	public static String asciiToEbcdic(String ascii) {
		return new String(Converter.asciiToEbcdic(ascii.getBytes()));
	}
	public static String asciiToBinary(String ascii) {
		return new String(Converter.asciiToBinary(ascii.getBytes()));
	}
	public static String asciiToHex(String ascii) {
		return new String(Converter.asciiToHex(ascii.getBytes()));
	}
	public static String hexToAscii(String hex) {
		if (hex.length() % 2 == 1) {
			MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Conversion Error", "Length of selection '"+hex+"' must be non");
			return null;
		}
		if (!hex.matches(REGEXP_HEX)) {
			MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Conversion Error", "Selection '"+hex+"' is not Hex");
			return null;
		}
		return new String(Converter.hexToAscii(hex.getBytes()));
	}
	public static String hexToBinary(String hex) {
		if (!hex.matches(REGEXP_HEX)) {
			MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Conversion Error", "Selection '"+hex+"' is not Hex");
			return null;
		}
		return new String(Converter.hexToBinary(hex.getBytes()));
	}
	public static String hexToEbcdic(String hex) {
		if (hex.length() % 2 == 1) {
			MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Conversion Error", "Length of selection '"+hex+"' must be non");
			return null;
		}
		if (!hex.matches(REGEXP_HEX)) {
			MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Conversion Error", "Selection '"+hex+"' is not Hex");
			return null;
		}
		return new String(Converter.hexToEbcdic(hex.getBytes()));
	}
	public static String hexToInteger(String hex) {
		if (!hex.matches(REGEXP_HEX)) {
			MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Conversion Error", "Selection '"+hex+"' is not Hex");
			return null;
		}
		return new BigInteger(hex, 16).toString(10);
	}
	public static String integerToHex(String num) {
		if (!num.matches(REGEXP_NUMERIC)) {
			MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Conversion Error", "Selection '"+num+"' is not numeric ([0-9]+)");
			return null;
		}
		String hex = new BigInteger(num, 10).toString(16).toUpperCase();
		return hex.length() %2 != 0 ? "0"+hex : hex;
	}
	public static String binaryToHex(String bits) {
		if (!bits.matches(REGEXP_BIT)) {
			MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Conversion Error", "Selection '"+bits+"' is not in Bits");
			return null;
		}
		return new String(Converter.binaryToHex(bits.getBytes()));
	}
	public static String binaryToAscii(String bits) {
		if (bits.length() % 8 != 0) {
			MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Conversion Error", "Length of selection '"+bits+"' must be multiple of eight");
			return null;
		}
		return new String(Converter.binaryToAscii(bits.getBytes()));
	}
	public static String binaryToEbcdic(String bits) {
		if (bits.length() % 8 != 0) {
			MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Conversion Error", "Length of selection '"+bits+"' must be multiple of eight");
			return null;
		}
		return new String(Converter.binaryToEbcdic(bits.getBytes()));
	}
}