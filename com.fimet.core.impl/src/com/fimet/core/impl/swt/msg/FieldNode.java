package com.fimet.core.impl.swt.msg;

import static com.fimet.core.impl.swt.msg.FieldTree.*;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.StyledString;

import com.fimet.commons.utils.StringUtils;
import com.fimet.core.IFieldParserManager;
import com.fimet.core.Manager;
import com.fimet.core.ISO8583.parser.Field;
import com.fimet.core.ISO8583.parser.IFieldParser;

public class FieldNode {
	private static IFieldParserManager fieldParserManager = Manager.get(IFieldParserManager.class);
	FieldNode parent;
	List<FieldNode> children;
	String idField;
	String key;
	String name;
	String value;
	boolean invalidId;
	boolean invalidLength;
	boolean invalidType;
	public FieldNode() {
		super();
	}
	public FieldNode(Field field, Integer idGroup) {
		super();
		idField = field.getIdField();
		value = field.getValue();
		IFieldParser fieldParser = fieldParserManager.getFieldParser(idGroup, idField);
		if (fieldParser != null) {
			name = fieldParser.getName();	
		}  else {
			name = null;
		}
		//name = fieldParser != null ? fieldParser.getName() : null;
		if (field.hasChildren()) {
			children = new ArrayList<>(field.getChildren().length);
			for (Field child : field.getChildren()) {
				add(new FieldNode(child, idGroup));
			}
		}
	}
	public FieldNode(String idField, String value) {
		super();
		this.idField = idField;
		this.value = value;
	}
	public FieldNode(FieldNode parent) {
		super();
		this.parent = parent;
	}
	public void assignGroup(Integer idGroup) {
		IFieldParser fieldParser = fieldParserManager.getFieldParser(idGroup, idField);
		name = fieldParser != null ? fieldParser.getName() : null;
	}
	public boolean hasChildren() {
		return children != null && !children.isEmpty();
	}
	public FieldNode[] getChildren() {
		if (!hasChildren()) {
			return null;
		}
		return children.toArray(new FieldNode[children.size()]);
	}
	public StyledString getStyledText() {
		String idField = "["+this.idField+"]";
		String name = "["+StringUtils.maxLength(this.name, 40)+"]";
		String length = "["+(this.value == null ? "0" : ""+this.value.length())+"]";
		String value = "["+(this.value == null ? "" : this.value)+"]";
		StyledString styledString = new StyledString()
			.append(idField,invalidId ? stylerError : stylerKey)
			.append(name, stylerName)
			.append(length, invalidLength ? stylerError : stylerLength)
			.append(value, invalidType ? stylerError : stylerValue)
		;
        return styledString;
	}
	public int remove(FieldNode node) {
		if (hasChildren()) {
			int i = children.indexOf(node);
			children.remove(node);
			node.parent = null;
			return i;
		}
		return -1;
	}
	public void add(FieldNode node) {
		if (children == null || children.isEmpty()) {
			children = new ArrayList<>();
		}
		node.parent = this;
		children.add(node);
	}
	public FieldNode getParent() {
		return parent;
	}
	public String getIdField() {
		return idField;
	}
	public String getName() {
		return name;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	@Override
	public String toString() {
		return "NodeField [" + idField+ ", parent=" + parent + ", children=" + children + "]";
	}
    
}
