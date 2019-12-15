package com.fimet.core.impl.rule;

public class OperatorContains implements IOperator {
	private static OperatorContains instance = new OperatorContains();
	public static OperatorContains getInstance() {
		return instance;
	}
	@Override
	public boolean apply(String value, String expected) {
		return value == null && expected == null || (value != null && value.toUpperCase().contains(expected));
	}

}
