package com.fimet.core.impl.rule;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import com.fimet.commons.exception.FimetException;
import com.fimet.core.entity.sqlite.Rule;
import com.fimet.core.net.ISocket;
import com.fimet.net.Activator;

public class MatcherRule {
	private Integer idResult;
	private String pattern;
	private IOperator operator;
	private String getterName;
	private List<MatcherRule> children;
	public MatcherRule(Rule rule) {
		this.idResult = rule.getIdResult();
		this.pattern = rule.getPattern();
		switch(rule.getOperator()) {
		case Rule.EQUALS:
			operator = OperatorEquals.getInstance();
			break;
		case Rule.CONTAINS:
			operator = OperatorContains.getInstance();
			break;
		case Rule.MATCHES:
			operator = OperatorMatches.getInstance();
			break;
		default:
			throw new FimetException("Invalid operand '"+rule.getOperator()+"' for rule "+rule);
		}
		if (rule.getProperty() == null || rule.getProperty().length() == 0) {
			throw new FimetException("Invalid null property for rule "+rule);
		}
		getterName = "get"+Character.toUpperCase(rule.getProperty().charAt(0))+rule.getProperty().substring(1);
	}
	public MatcherRule findMatch(ISocket connection) {
		if (hasChildren()) {
			if (!operator.apply(getValue(getterName, connection), pattern)) return null;
			for (MatcherRule child : children) {
				if ((child = child.findMatch(connection)) != null) return child;
			}
			return null;
		} else {
			return operator.apply(getValue(getterName, connection), pattern) ? this : null;
		}
	}
	public MatcherRule findMatch(String value) {
		if (hasChildren()) {
			if (!operator.apply(value, pattern)) return null;
			for (MatcherRule child : children) {
				if ((child = child.findMatch(value)) != null) return child;
			}
			return null;
		} else {
			return operator.apply(value, pattern) ? this : null;
		}
	}
	public Integer getIdResult() {
		return idResult;
	}
	public String getValue(String property, ISocket connection) {
		if (connection == null) {
			return null;
		}
		try {
			Method getter = connection.getClass().getMethod(getterName);
			return getter.invoke(connection)+"";
		} catch (Exception e) {
			Activator.getInstance().warning("Error",e);
			return null;
		}
	}
	public boolean hasChildren() {
		return children != null && !children.isEmpty();
	}
	public List<MatcherRule> getChildren() {
		return children;
	}
	public MatcherRule add(MatcherRule rule) {
		if (children == null) {
			children = new LinkedList<MatcherRule>();
		}
		children.add(rule);
		return rule;
	}
	public String toString() {
		return getterName+" "+operator+" "+pattern + " = "+idResult;
	}
}
