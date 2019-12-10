package com.fimet.commons.numericparser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author <a href="mailto:marcoasb99@ciencias.unam.mx">Marco A. Salazar</a>
 *
 */
public abstract class NumericParser implements INumericParser {

	public static final Map<Integer, INumericParser> parsers = new HashMap<>();
	public static final INumericParser DEC = new NumericParserDec(0,"Decimal");
	public static final INumericParser DEC_DOUBLE = new NumericParserDecDouble(1,"Decimal Double");
	public static final INumericParser DEC_HALF = new NumericParserDecHalf(2,"Decimal Half");
	public static final INumericParser HEX = new NumericParserHex(3,"Hex");
	public static final INumericParser HEX_DOUBLE = new NumericParserHexDouble(4,"Hex Double");
	public static final INumericParser HEX_HALF = new NumericParserHexHalf(5,"Hex Half");
	
	public static INumericParser get(int id) {
		return parsers.get(id);
	}
	public static List<INumericParser> getParsers() {
		List<INumericParser> list = new ArrayList<>(parsers.size());
		for (Map.Entry<Integer, INumericParser> c : parsers.entrySet()) {
			list.add(c.getValue());
		}
		return list;
	}
	private int id;
	private String name;
	NumericParser(int id, String name) {
		this.id = id;
	    this.name = name;
	    parsers.put(id, this);
	}
	public String getName() {
		return name;
	}
	public int getId() {
		return id;
	}
	@Override
	public String toString() {
	    return name;
	}
}