package com.atenea.evaluator.type;

public class STRING extends TypeString {
	private String value;
	public STRING(String value) {
		this.value = value;
	}
	@Override
	public String evaluate() {
		return value;
	}
}
