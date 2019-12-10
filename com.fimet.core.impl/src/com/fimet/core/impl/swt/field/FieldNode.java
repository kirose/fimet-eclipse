package com.fimet.core.impl.swt.field;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;

import com.fimet.commons.Color;
import com.fimet.commons.DefaultStyler;
import com.fimet.core.entity.sqlite.FieldFormat;

public class FieldNode {

	static Styler STYLER_ID;
	static Styler STYLER_ERROR;
    static Styler STYLER_KEY;
    static Styler STYLER_NAME;
    static Styler STYLER_LENGTH;
    static Styler STYLER_CLASS;
    static {
    	init();
    }
	private static void init() {
		if (STYLER_ERROR == null) {
			STYLER_ID = new DefaultStyler(Color.PURPLE, null);//StyledString.createColorRegistryStyler(JFacePreferences.HYPERLINK_COLOR, null);
			STYLER_ERROR = new DefaultStyler(Color.RED, null);
			STYLER_KEY = new DefaultStyler(Color.PURPLE, null);
			STYLER_NAME = new DefaultStyler(Color.CYAN, null);//
			STYLER_LENGTH = new DefaultStyler(Color.GRAY2, null);
			STYLER_CLASS = new DefaultStyler(Color.BLUE_LIGHT2, null);//StyledString.COUNTER_STYLER;//
		}
	}
	
	FieldFormat field;
	FieldNode parent;
	List<FieldNode> children;
	public FieldNode(FieldFormat field) {
		super();
		this.field = field;
		if (field.getChilds() != null) {
			children = new ArrayList<>();
		}
	}
	public boolean hasChildren() {
		return children != null && !children.isEmpty();
	}
	public FieldFormat getFieldFormat() {
		return field;
	}
	public FieldFormat getField() {
		return field;
	}
	public FieldNode[] getChildren() {
		if (children == null || children.isEmpty()) {
			return null;
		}
		return children.toArray(new FieldNode[children.size()]);
	}
	public FieldNode getLastChild() {
		if (hasChildren()) {
			return children.get(children.size()-1);
		}
		return null;
	}
	public StyledString getStyledText() {
        StyledString s = new StyledString();
        s.append("["+field.getIdField()+"]", STYLER_ID);
        s.append("["+field.getName()+"]", STYLER_NAME);
        s.append("["+field.getLength()+"]",STYLER_LENGTH);
        s.append("["+field.getClassParser().substring(field.getClassParser().lastIndexOf('.')+1)+"]",STYLER_CLASS);
        s.append("["+field.getIdOrder()+"]",STYLER_ID);
        return s;
	}
	public void removeChild(FieldNode node) {
		if (children == null || children.isEmpty()) {
			return;
		}
		children.remove(node);
		node.parent = null;
		updateChildsField();			
	}
	public void addChild(FieldNode node) {
		if (children == null || children.isEmpty()) {
			children = new ArrayList<>();
		}
		children.add(node);
		if (field.getChilds() == null) {
			field.setChilds(node.field.getKey());
		} else {
			field.setChilds(field.getChilds()+","+node.field.getKey());
		}
		node.parent = this;
	}
    void updateChildsField() {
    	if (hasChildren()) {
    		StringBuilder sb = new StringBuilder();
    		for (FieldNode child : children) {
    			sb.append(child.field.getKey()).append(',');
    		}
    		sb.delete(sb.length()-1, sb.length());
    		field.setChilds(sb.toString());
    	} else {
    		field.setChilds(null);
    	}
	}
	@Override
	public String toString() {
		return (field != null ? (field.getIdField() +"") : "null");
	}
	public FieldNode getNode(FieldFormat field) {
		if (hasChildren()) {
			for (FieldNode node : children) {
				if (node.field.equals(field)) {
					return node;
				}
			}
		}
		return null;
	}
	public FieldNode remove(FieldFormat group) {
		FieldNode node = getNode(group);
		if (node != null) {
			children.remove(node);
			return node;
		}
		return null;
	}
	public FieldNode remove(FieldNode field) {
		if (hasChildren()) {
			int index = children.indexOf(field);
			if (index != -1) {
				FieldNode node = children.remove(index);
				createChildren();
				return node;
			}
		}
		return null;
	}
	public FieldNode add(FieldFormat field) {
		if (children == null)
			children = new ArrayList<>();
		FieldNode node = new FieldNode(field);
		node.parent = this;
		children.add(node);
		createChildren();
		return node;
	}
	private void createChildren() {
		if (hasChildren()) {
			StringBuilder s = new StringBuilder();
			for (FieldNode f : children) {
				s.append(f.field.getKey()).append(',');
			}
			s.delete(s.length()-1, s.length());
			field.setChilds(s.toString());
		} else {
			field.setChilds(null);
		}
	}
}
