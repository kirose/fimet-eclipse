package com.fimet.evaluator.type;

public class INTEGER extends TypeInteger {
	private Integer value;
	public INTEGER(Integer value) {
		this.value = value;
	}
	@Override
	public Integer evaluate() {
		return value;
	}
}
