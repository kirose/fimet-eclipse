package com.fimet.core.impl.rule;

public class OperatorEquals implements IOperator {
	private static OperatorEquals instance = new OperatorEquals();
	public static OperatorEquals getInstance() {
		return instance;
	}
	@Override
	public boolean apply(String value, String expected) {
		return value == null && expected == null || (value != null && value.toUpperCase().equals(expected));
	}

}
