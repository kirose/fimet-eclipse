package com.fimet.core.impl.preferences.rule;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.StyledString;

import com.fimet.commons.exception.FimetException;
import com.fimet.core.IRuleManager;
import com.fimet.core.Manager;
import com.fimet.core.entity.sqlite.Rule;

class RuleNode {

	String operator;
	String value;
	Rule rule;
	RuleNode parent;
	List<RuleNode> children;
	public RuleNode(Rule rule) {
		super();
		this.rule = rule;
		updateLabels();
	}
	public void updateLabels() {
		switch(rule.getOperator()) {
		case Rule.EQUALS:
			operator = "equals";
			break;
		case Rule.CONTAINS:
			operator = "contains";
			break;
		case Rule.MATCHES:
			operator = "matches";
			break;
		default:
			throw new FimetException("Invalid operand '"+rule.getOperator()+"' for rule "+rule);
		}
		if (rule.getIdResult() != null) {
			value = Manager.get(IRuleManager.class).getResult(rule.getIdField(), rule.getIdResult());
		}		
	}
	public boolean hasChildren() {
		return children != null && !children.isEmpty();
	}
	public Rule getRule() {
		return rule;
	}
	public RuleNode[] getChildren() {
		if (children == null || children.isEmpty()) {
			return null;
		}
		return children.toArray(new RuleNode[children.size()]);
	}
	public RuleNode getLastChild() {
		if (hasChildren()) {
			return children.get(children.size()-1);
		}
		return null;
	}
	public StyledString getStyledText() {
		StyledString s = new StyledString();
		s.append("if ", RulePage.STYLER_RESERVED_WORDS);
		s.append("( "+rule.getProperty(), RulePage.STYLER_TERMINAL);
		s.append(" "+operator, RulePage.STYLER_RESERVED_WORDS);
		s.append(" \""+rule.getPattern()+"\"", RulePage.STYLER_TEXT);
		s.append(" )", RulePage.STYLER_TERMINAL);
		if (!hasChildren()) {
			s.append(" assign ", RulePage.STYLER_RESERVED_WORDS);
			s.append(value, RulePage.STYLER_VALUE);
		}
        return s;
	}
	public void removeChild(RuleNode node) {
		if (children == null || children.isEmpty()) {
			return;
		}
		children.remove(node);
		node.parent = null;
	}
	public void addChild(RuleNode node) {
		if (children == null || children.isEmpty()) {
			children = new ArrayList<>();
		}
		children.add(node);
		node.parent = this;
	}
	@Override
	public String toString() {
		return "FieldNode [rule=" + (rule != null ? rule.getIdField() : "null") + ", parent=" + parent + ", children=" + children + ", nodes="
				+ children + "]";
	}
	public RuleNode getNode(Rule rule) {
		if (hasChildren()) {
			for (RuleNode node : children) {
				if (node.rule.equals(rule)) {
					return node;
				}
			}
		}
		return null;
	}
	public RuleNode remove(Rule group) {
		RuleNode node = getNode(group);
		if (node != null) {
			children.remove(node);
			return node;
		}
		return null;
	}
	public RuleNode remove(RuleNode rule) {
		if (hasChildren()) {
			int index = children.indexOf(rule);
			if (index != -1) {
				RuleNode node = children.remove(index);
				return node;
			}
		}
		return null;
	}
	public RuleNode add(RuleNode node) {
		if (children == null)
			children = new ArrayList<>();
		node.parent = this;
		children.add(node);
		return node;
	}
}
