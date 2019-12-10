package com.atenea.evaluator.type;

public class DOUBLE extends TypeDouble {
	private Double value;
	public DOUBLE(Double value) {
		this.value = value;
	}
	@Override
	public Double evaluate() {
		return value;
	}
}
