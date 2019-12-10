package com.fimet.core.ISO8583.parser;

import java.util.ArrayList;
import java.util.List;

import com.fimet.core.ISO8583.parser.MessageFields.Node;
import com.fimet.core.ISO8583.parser.MessageFields.Parent;


public class FieldTree {
	List<Field> roots;
	MessageFields fields;
	FieldTree(MessageFields fields){
		this.fields = fields;
		this.roots = new ArrayList<>();
		createTree();
	}
	private void createTree() {
		Node node = fields.head;
		Field field;
		while ((node = node.next) != null) {
			field = new Field(node.idField, node.bytes);
			roots.add(field);
			if (node.hasChildren()) {
				addChildren(field, node.idField, ((Parent)node).head);
			}
		}
	}
	private void addChildren(Field parent, String idParent, Node head) {
		Node node = head;
		Field child;
		while ((node = node.next) != null) {
			child = new Field(node.idField, node.bytes);
			parent.add(child);
			if (node.hasChildren()) {
				addChildren(child, node.idField, ((Parent)node).head);
			}
		}
	}
	public List<Field> getRoots(){
		return roots;
	}
}
