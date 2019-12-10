package com.fimet.core.usecase;

import com.google.gson.annotations.JsonAdapter;
import com.fimet.core.json.adapter.ValidationAdapter;

@JsonAdapter(ValidationAdapter.class)
public class Validation {
	private String name;
	private String operator;
	private String expression;
	private Object expected;
	public Validation() {
		super();
	}
	public Validation(String name, String expression, Object expected) {
		super();
		this.name = name;
		this.expression = expression;
		this.expected = expected;
		this.operator = "==";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	public Object getExpected() {
		return expected;
	}
	public void setExpected(Object expected) {
		this.expected = expected;
	}
	@Override
	public String toString() {
		return "{\"name\":"+name+",\"expression\":"+expression+",\"expected\":"+expected+"}";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((expression == null) ? 0 : expression.hashCode());
		result = prime * result + ((operator == null) ? 0 : operator.hashCode());
		result = prime * result + ((expected == null) ? 0 : expected.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Validation other = (Validation) obj;
		if (expected == null) {
			if (other.expected != null)
				return false;
		} else if (!expected.equals(other.expected))
			return false;
		if (expression == null) {
			if (other.expression != null)
				return false;
		} else if (!expression.equals(other.expression))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
