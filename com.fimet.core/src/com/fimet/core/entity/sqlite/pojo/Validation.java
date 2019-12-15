package com.fimet.core.entity.sqlite.pojo;

import com.fimet.commons.console.Console;
import com.fimet.commons.console.IPrintable;

public class Validation implements IPrintable {

	public static final String EQUALS = "==";
	public static final String DIFFERENT = "!=";
	public static final String GREATER_THAN = ">";
	public static final String GREATER_EQUALS_THAN = ">=";
	public static final String LESS_THAN = "<";
	public static final String LESS_EQUALS_THAN = "<=";
	
	public static final String EQUALS_NAME = "equals";
	public static final String DIFFERENT_NAME = "different";
	public static final String GREATER_THAN_NAME = "greaterThan";
	public static final String GREATER_EQUALS_THAN_NAME = "greaterEqualsThan";
	public static final String LESS_THAN_NAME = "lessThan";
	public static final String LESS_EQUALS_THAN_NAME = "lessEqualsThan";
	
	private String name;
	private String operator; 
	private String expected;
	private String value;
	private Boolean fail = Boolean.TRUE;
	public Validation() {}
	public Validation(String name) {
		super();
		this.name = name;
	}
	public Validation(String name, Object expected, Object value) {
		super();
		this.name = name;
		safeParameters(expected, value);
		equals(expected, value);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExpected() {
		return expected;
	}
	public void setExpected(String expected) {
		this.expected = expected;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Boolean getFail() {
		return fail;
	}
	public void setFail(Boolean fail) {
		this.fail = fail;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public void print(Console console) {
		console.getPrintStream().append("\t[");
		if (fail) {
			console.getPrintStreamError().append("FALIURE");
		} else {
			console.getPrintStreamDebug().append("SUCCESSFUL");
		}
		console.getPrintStream().append("]["+name + "][" + expected + "]["+operator + "][" + value +"]\n");
	}
	public String toString() {
		String sval = "";
		sval += "\t"+(fail ? "FAILURE" : "SUCCESSFUL")+"\t"+name + "\n";
		sval += "\t[" + expected + ("]["+operator + "][") + value +"]\n";
		return sval;
	}
	private void safeParameters(Object expected, Object value) {
		if (expected instanceof String) {
			this.expected = expected == null ? "null" : ""+expected;
		} else if (value instanceof Number) {
			this.expected = ((Number)expected).doubleValue()+"";
		} else if (expected != null){
			this.expected = expected.toString();
		} else {
			this.expected = "null";
		}
		if (value instanceof String) {
			this.value = value == null ? "null" : ""+value;
		} else if (value instanceof Number) {
			this.value = ((Number)value).doubleValue()+"";
		} else if (value != null){
			this.value = value.toString();
		} else {
			this.value = "null";
		}
	}
	public void equals(Object expected, Object value) {
		safeParameters(expected, value);
		operator = EQUALS;
		if (expected instanceof Number) {
			if (value instanceof Number) {
				fail = ((Number)expected).doubleValue() != ((Number)value).doubleValue();
			} else {
				fail = true;
			}
		} else {
			if (expected == null && value == null) {
				fail = false;
			} else if (expected != null) {
				fail = !expected.equals(value);
			} else {
				fail = !value.equals(expected);
			}
		}
		printToConsole();
	}
	public void different(Object expected, Object value) {
		safeParameters(expected, value);
		operator = DIFFERENT;
		if (expected instanceof Number) {
			if (value instanceof Number) {
				fail = ((Number)expected).doubleValue() == ((Number)value).doubleValue();
			} else {
				fail = true;
			}
		} else {
			if (expected == null && value == null) {
				fail = false;
			} else if (expected != null) {
				fail = expected.equals(value);
			} else {
				fail = value.equals(expected);
			}
		}
		printToConsole();
	}
	public void greaterThan(Number expected, Number value) {
		safeParameters(expected, value);
		operator = GREATER_THAN;
		if (expected == null && value == null) {
			fail = false;
		} else if (expected != null) {
			fail = value != null ? expected.doubleValue() > value.doubleValue() : true;
		} else {
			fail = true;
		}
		printToConsole();
	}
	public void greaterEqualsThan(Number expected, Number value) {
		safeParameters(expected, value);
		operator = GREATER_EQUALS_THAN;
		if (expected == null && value == null) {
			fail = false;
		} else if (expected != null) {
			fail = value != null ? expected.doubleValue() >= value.doubleValue() : true;
		} else {
			fail = true;
		}
		printToConsole();
	}
	public void lessThan(Number expected, Number value) {
		safeParameters(expected, value);
		operator = LESS_THAN;
		if (expected == null && value == null) {
			fail = false;
		} else if (expected != null) {
			fail = value != null ? expected.doubleValue() < value.doubleValue() : true;
		} else {
			fail = true;
		}
		printToConsole();
	}
	public void lessEqualsThan(Number expected, Number value) {
		safeParameters(expected, value);
		operator = LESS_EQUALS_THAN;
		if (expected == null && value == null) {
			fail = false;
		} else if (expected != null) {
			fail = value != null ? expected.doubleValue() <= value.doubleValue() : true;
		} else {
			fail = true;
		}
		printToConsole();
	}
	private void printToConsole() {
		if (Console.isEnabledInfo()) {
			Console.getInstance().info(getClass(), this);
		}
	}
	public static String getOperatorName(String operator) {
		if (operator == null || EQUALS.equals(operator)) {
			return EQUALS_NAME;
		} else if (DIFFERENT.equals(operator)) {
			return DIFFERENT_NAME;
		} else if (GREATER_THAN.equals(operator)) {
			return GREATER_THAN_NAME;
		} else if (GREATER_EQUALS_THAN.equals(operator)) {
			return GREATER_EQUALS_THAN_NAME;
		} else if (LESS_THAN.equals(operator)) {
			return LESS_THAN_NAME;
		} else if (LESS_EQUALS_THAN.equals(operator)) {
			return LESS_EQUALS_THAN_NAME;
		} else {
			return EQUALS_NAME;
		}
	}
}
