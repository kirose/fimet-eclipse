package com.fimet.core.impl.rule;

public class OperatorMatches implements IOperator {
	private static OperatorMatches instance = new OperatorMatches();
	public static OperatorMatches getInstance() {
		return instance;
	}
	@Override
	public boolean apply(String value, String expected) {
		return value == null && expected == null || (value != null && value.toUpperCase().matches(expected));
	}
}
