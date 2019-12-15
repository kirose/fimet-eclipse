package com.fimet.commons.utils;

import java.util.ArrayList;
import java.util.List;

public final class StringUtils {

	private static final int PAD_LIMIT = 8192;
	public static final String EMPTY = "";
	public static final String SPACE = " ";
	
	private StringUtils() {}
	public static boolean isBlank(CharSequence seq) {
		return seq == null || seq.length() == 0;
	}
	public static String escapeNull(String value) {
		return value == null ? "" : value;
	}
	public static String escapeNull(String value, String replace) {
		return value == null ? replace : value;
	}
	public static String escapeNull(Number value) {
		return value == null ? "" : value+"";
	}
	public static String escapeNull(Number value, String replace) {
		return value == null ? replace : value+"";
	}
    // Empty checks
    //-----------------------------------------------------------------------
    /**
     * <p>Checks if a CharSequence is empty ("") or null.</p>
     *
     * <pre>
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
     * </pre>
     *
     * <p>NOTE: This method changed in Lang version 2.0.
     * It no longer trims the CharSequence.
     * That functionality is available in isBlank().</p>
     *
     * @param cs  the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is empty or null
     * @since 3.0 Changed signature from isEmpty(String) to isEmpty(CharSequence)
     */
    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * <p>Checks if a CharSequence is not empty ("") and not null.</p>
     *
     * <pre>
     * StringUtils.isNotEmpty(null)      = false
     * StringUtils.isNotEmpty("")        = false
     * StringUtils.isNotEmpty(" ")       = true
     * StringUtils.isNotEmpty("bob")     = true
     * StringUtils.isNotEmpty("  bob  ") = true
     * </pre>
     *
     * @param cs  the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is not empty and not null
     * @since 3.0 Changed signature from isNotEmpty(String) to isNotEmpty(CharSequence)
     */
    public static boolean isNotEmpty(final CharSequence cs) {
        return !isEmpty(cs);
    }

    /**
     * <p>Returns padding using the specified delimiter repeated
     * to a given length.</p>
     *
     * <pre>
     * StringUtils.repeat('e', 0)  = ""
     * StringUtils.repeat('e', 3)  = "eee"
     * StringUtils.repeat('e', -2) = ""
     * </pre>
     *
     * <p>Note: this method does not support padding with
     * <a href="http://www.unicode.org/glossary/#supplementary_character">Unicode Supplementary Characters</a>
     * as they require a pair of {@code char}s to be represented.
     * If you are needing to support full I18N of your applications
     * consider using {@link #repeat(String, int)} instead.
     * </p>
     *
     * @param ch  character to repeat
     * @param repeat  number of times to repeat char, negative treated as zero
     * @return String with repeated character
     * @see #repeat(String, int)
     */
    public static String repeat(final char ch, final int repeat) {
        if (repeat <= 0) {
            return EMPTY;
        }
        final char[] buf = new char[repeat];
        for (int i = repeat - 1; i >= 0; i--) {
            buf[i] = ch;
        }
        return new String(buf);
    }
	
    /**
     * <p>Left pad a String with spaces (' ').</p>
     *
     * <p>The String is padded to the size of {@code size}.</p>
     *
     * <pre>
     * StringUtils.leftPad(null, *)   = null
     * StringUtils.leftPad("", 3)     = "   "
     * StringUtils.leftPad("bat", 3)  = "bat"
     * StringUtils.leftPad("bat", 5)  = "  bat"
     * StringUtils.leftPad("bat", 1)  = "bat"
     * StringUtils.leftPad("bat", -1) = "bat"
     * </pre>
     *
     * @param str  the String to pad out, may be null
     * @param size  the size to pad to
     * @return left padded String or original String if no padding is necessary,
     *  {@code null} if null String input
     */
    public static String leftPad(final String str, final int size) {
        return leftPad(str, size, ' ');
    }

    /**
     * <p>Left pad a String with a specified character.</p>
     *
     * <p>Pad to a size of {@code size}.</p>
     *
     * <pre>
     * StringUtils.leftPad(null, *, *)     = null
     * StringUtils.leftPad("", 3, 'z')     = "zzz"
     * StringUtils.leftPad("bat", 3, 'z')  = "bat"
     * StringUtils.leftPad("bat", 5, 'z')  = "zzbat"
     * StringUtils.leftPad("bat", 1, 'z')  = "bat"
     * StringUtils.leftPad("bat", -1, 'z') = "bat"
     * </pre>
     *
     * @param str  the String to pad out, may be null
     * @param size  the size to pad to
     * @param padChar  the character to pad with
     * @return left padded String or original String if no padding is necessary,
     *  {@code null} if null String input
     * @since 2.0
     */
    public static String leftPad(final String str, final int size, final char padChar) {
        if (str == null) {
            return null;
        }
        final int pads = size - str.length();
        if (pads <= 0) {
            return str; // returns original String when possible
        }
        if (pads > PAD_LIMIT) {
            return leftPad(str, size, String.valueOf(padChar));
        }
        return repeat(padChar, pads).concat(str);
    }

    /**
     * <p>Left pad a String with a specified String.</p>
     *
     * <p>Pad to a size of {@code size}.</p>
     *
     * <pre>
     * StringUtils.leftPad(null, *, *)      = null
     * StringUtils.leftPad("", 3, "z")      = "zzz"
     * StringUtils.leftPad("bat", 3, "yz")  = "bat"
     * StringUtils.leftPad("bat", 5, "yz")  = "yzbat"
     * StringUtils.leftPad("bat", 8, "yz")  = "yzyzybat"
     * StringUtils.leftPad("bat", 1, "yz")  = "bat"
     * StringUtils.leftPad("bat", -1, "yz") = "bat"
     * StringUtils.leftPad("bat", 5, null)  = "  bat"
     * StringUtils.leftPad("bat", 5, "")    = "  bat"
     * </pre>
     *
     * @param str  the String to pad out, may be null
     * @param size  the size to pad to
     * @param padStr  the String to pad with, null or empty treated as single space
     * @return left padded String or original String if no padding is necessary,
     *  {@code null} if null String input
     */
    public static String leftPad(final String str, final int size, String padStr) {
        if (str == null) {
            return null;
        }
        if (isEmpty(padStr)) {
            padStr = SPACE;
        }
        final int padLen = padStr.length();
        final int strLen = str.length();
        final int pads = size - strLen;
        if (pads <= 0) {
            return str; // returns original String when possible
        }
        if (padLen == 1 && pads <= PAD_LIMIT) {
            return leftPad(str, size, padStr.charAt(0));
        }

        if (pads == padLen) {
            return padStr.concat(str);
        } else if (pads < padLen) {
            return padStr.substring(0, pads).concat(str);
        } else {
            final char[] padding = new char[pads];
            final char[] padChars = padStr.toCharArray();
            for (int i = 0; i < pads; i++) {
                padding[i] = padChars[i % padLen];
            }
            return new String(padding).concat(str);
        }
    }
    public static String rightTrim(String text, char ch) {
    	if (text == null || text.length() == 0) {
    		return text;
    	}
    	int index = text.length();
    	while (index > 0 && text.charAt(index-1) == ch) {
    		index--;
    	}
    	return index == 0 ? "" : text.substring(0,index);
    }
    public static String leftTrim(String text, char ch) {
    	if (text == null || text.length() == 0) {
    		return text;
    	}
    	int ln = text.length();
    	int index = 0;
    	while (index < ln && text.charAt(index) == ch) {
    		index++;
    	}
    	return index == 0 ? "" : text.substring(index,ln);
    }
    public static String fixedLength(String s, int ln) {
    	int sln = s != null ? s.length() : 0;
    	if (sln > ln) {
    		return ln > 3 ? s.substring(0,ln-3) + "..." : s.substring(0,ln);
    	}
    	StringBuilder sb = new StringBuilder();
    	if (s != null) {
    		sb.append(s);
    	}
    	for (int i = 0; i < ln-sln; i++) {
			sb.append(' ');
		}
    	return sb.toString();
    }
    public static String maxLength(String s, int ln) {
    	if (s == null) {
    		return "";
    	} else if (s.length() <= ln) {
    		return s;
    	} else {
    		return ln > 3 ? s.substring(0,ln-3) + "..." : s.substring(0,ln);
    	}
    }
    public static String join(String[] a, char separator, int lengthPad, char pad) {
    	if (a != null && a.length == 0) {
        	StringBuilder sb = new StringBuilder();
        	for (String s : a) {
				sb.append(leftPad(s, lengthPad,pad)).append(separator);
			}
        	sb.delete(sb.length()-1, sb.length());
        	return sb.toString();
    	}
    	return "";
    }
    public static String join(String[] a, char separator) {
    	if (a != null && a.length == 0) {
        	StringBuilder sb = new StringBuilder();
        	for (String s : a) {
				sb.append(s).append(separator);
			}
        	sb.delete(sb.length()-1, sb.length());
        	return sb.toString();
    	}
    	return "";
    }
    public static String join(List<String> l, char separator) {
    	if (l != null && !l.isEmpty()) {
        	StringBuilder sb = new StringBuilder();
        	for (String s : l) {
				sb.append(s).append(separator);
			}
        	sb.delete(sb.length()-1, sb.length());
        	return sb.toString();
    	}
    	return "";
    }
    public static String join(List<String> l) {
    	if (l != null && !l.isEmpty()) {
        	StringBuilder sb = new StringBuilder();
        	for (String s : l) {
				sb.append(s).append(',');
			}
        	sb.delete(sb.length()-1, sb.length());
        	return sb.toString();
    	}
    	return "";
    }
    public static List<String> expand(String s) {
    	return expand(s,",");
    }
    public static List<String> expand(String s, String regexpSeparator) {
    	if (s != null && s.trim().length() > 0) {
    		String[] parts = s.split(regexpSeparator);
    		List<String> l = new ArrayList<>(parts.length);
    		for (String p : parts) {
				l.add(p);
			}
        	return l;
    	} else {
    		return null;
    	}
    }
}
