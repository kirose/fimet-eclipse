package com.fimet.core.ISO8583.parser;

import java.util.ArrayList;
import java.util.List;

import com.fimet.commons.console.Console;
import com.fimet.core.IFieldParserManager;
import com.fimet.core.Manager;

public class MessageFields implements Cloneable {
	//static final String REGEXP_ADDRESS = "[0-9]+(\\.[a-Za-z0-9_$]+)*";
	static IFieldParserManager fieldParserManager = Manager.get(IFieldParserManager.class);
	Node head;
	Integer idGroup;
	MessageFields(Integer idGroup) {
		this.idGroup = idGroup;
		this.head = new Leaf();
	}
	MessageFields(IMessage message) {
		this.idGroup = message != null && message.getParser() != null ? message.getParser().getIdGroup() : null;
		this.head = new Leaf();
	}
	/**
	 * Return true if idField has Children, false in any other case
	 * @param idField
	 * @return boolean
	 */
	boolean hasChildren(String idField) {
		Node node = find(idField);
		return node != null && node.hasChildren();
	}
	/**
	 * Returns all (bitmaped) fields id [3,4,6,7,...]
	 * @return List<String>
	 */
	List<String> getIdChildren() {
		List<String> ids = new ArrayList<>();
		Node node = head;
		while ((node = node.next) != null) {
			ids.add(node.idField);
		}
		return ids;
	}
	/**
	 * Returns all children fields id of idField
	 * @param idField
	 * @return List<String> 
	 */
	List<String> getIdChildren(String idField) {
		Node node = find(idField);
		if (node != null && node.hasChildren()) {
			node = ((Parent)node).head;
			List<String> ids = new ArrayList<>();
			while ((node = node.next) != null) {
				ids.add(node.idField);
			}
			return ids;
		} else {
			return null;
		}
	}
	/**
	 * Returns an int array with all bitmaped fields id  
	 * @return
	 */
	int[] getBitmap() {
		Node node = head;
		int[] bitmap = new int[size()];
		int i = 0;
		while((node = node.next)!=null) {
			bitmap[i++] = node.id;
		}
		return bitmap;
	}
	/**
	 * Returns the value (byte[]) from the field idField
	 * @param idField
	 * @return byte[] value of the field idField
	 */
	byte[] get(String idField) {
		Node node = find(idField);
		return node != null ? node.bytes : null;
	}
	/**
	 * Replace the value of the idField if idField exists
	 * @param idField
	 * @param value the value to be replaced
	 */
	void replace(String idField, byte[] value) {
		Node node = find(idField);
		if (node != null) {
			node.bytes = value;
		}
	}
	/**
	 * Returns the number of children bitmaped
	 * @return
	 */
	int size() {
		int size = 0;
		Node node = head;
		while((node = node.next)!=null) {size++;}
		return size;
	}
	/**
	 * Returns true has children, false in any other case
	 * @return
	 */
	boolean isEmpty() {
		return head.next == null;
	}
	Node find(String idField) {
		short[] address = getAddress(idField);
		if (address == null) return null;
		if (address.length == 1) {
			return find(head, address[0]);	
		} else {
			int i = 0;
			int last = address.length -1;
			Node node = head;
			while (i < last) {
				node = find(node, address[i++]);
				if (node == null || !(node instanceof Parent)) return null;
				node = ((Parent)node).head;
			}
			return find(node, address[last]);
		}
	}
	/**
	 * Remove the idField if exists
	 * @param idField
	 * @return
	 */
	Node remove(String idField) {
		short[] address = getAddress(idField);
		if (address == null) return null;
		if (address.length == 1) {
			return remove(head, address[0]);	
		} else {
			int i = 0;
			int last = address.length -1;
			Node node = head;
			while (i < last) {
				node = find(node, address[i++]);
				if (node == null || !(node instanceof Parent)) return null;
				node = ((Parent)node).head;
			}
			return remove(node, address[last]);
		}
	}
	/**
	 * Insert the idField with value into the tree
	 * @param idField
	 * @param value
	 * @return
	 */
	Node insert(String idField, byte[] value) {
		short[] address = getAddress(idField);
		if (address == null) {
			//throw new ParserException("Unknow field id "+idField);
			Console.getInstance().warning(MessageFields.class, "Unknow field id "+idField);
			//Activator.getInstance().warning("Unknow field id "+idField);
			return null;
		}
		if (address.length == 1) {
			return insert(head, idField, address[0], value, true);	
		} else {
			int i = 0;
			int last = address.length -1;
			int start = 0;
			Node node = head;
			while (i < last) {
				start = idField.indexOf('.', start);
				node = getOrInsert(node, idField.substring(0, start++), address[i++], false);
				node = ((Parent)node).head;
			}
			return insert(node, idField, address[last], value, true);
		}
	}
	/**
	 * Busca el nodo o lo inserta si no existe
	 * @param head
	 * @param idField
	 * @param id
	 * @param leaf
	 * @return
	 */
	Node getOrInsert(Node head, String idField, short id, boolean leaf) {
		Node node = head;
		while (node.next != null && node.next.id < id) {
			node = node.next;
		}
		if (node.next != null) {
			if (node.next.id == id) {
				if (!leaf && !(node.next instanceof Parent)) {
					Node old = node.next;
					node.next = new Parent(idField, node.next.id, node.next.bytes);
					node.next.next = old.next;
					old.next = null;
				}
				return node.next;
			} else {
				Node next = node.next;
				node.next = leaf ? new Leaf(idField, id, null) : new Parent(idField, id, null);
				node.next.next = next;
				return node.next;
			}
		} else {
			return node.next = leaf ? new Leaf(idField, id, null) : new Parent(idField, id, null);
		}
	}
	/**
	 * Returns the node with the id idField, if the field not exists it inserts idField 
	 * @param head
	 * @param idField
	 * @param id
	 * @param value
	 * @param leaf
	 * @return the node idField
	 */
	Node insert(Node head, String idField, short id, byte[] value, boolean leaf) {
		Node node = head;
		while (node.next != null && node.next.id < id) {
			node = node.next;
		}
		if (node.next != null) {
			if (node.next.id == id) {
				node.next.bytes = value;
				if (!leaf && !(node.next instanceof Parent)) {
					Node old = node.next;
					node.next = new Parent(idField, node.next.id, node.next.bytes);
					node.next.next = old.next;
					old.next = null;
				}
				return node.next;
			} else {
				Node next = node.next;
				node.next = leaf ? new Leaf(idField, id, value) : new Parent(idField, id, value);
				node.next.next = next;
				return node.next;
			}
		} else {
			return node.next = leaf ? new Leaf(idField, id, value) : new Parent(idField, id, value);
		}
	}
	/**
	 * Find the Node (id) in the List head
	 * @param head
	 * @param id
	 * @return
	 */
	Node find(Node head, short id) {
		Node node = head;
		while ((node = node.next) != null) if (node.id == id) return node;
		return null;
	}
	/**
	 * Remove the Node (id) in the List head
	 * @param head
	 * @param id
	 * @return
	 */
	Node remove(Node head, short id) {
		Node node = head;
		while (node.next != null && node.next.id < id) {
			node = node.next;
		}
		if (node.next != null && node.next.id == id) {
			Node item = node.next; 
			node.next = node.next.next;
			item.next = null;
			return item;
		}
		return null;
	}
	/**
	 * Returns the address of idField</br>
	 * The address is an short array</br>
	 * Each address[i] is an id of the field</br> 
	 * @param idField
	 * @return short[] address
	 */
	short[] getAddress(String idField) {
		IFieldParser fieldParser = fieldParserManager.getFieldParser(idGroup, idField);
		if (fieldParser == null) {
			return null;
		}
		return fieldParser.getAddress();
	}
	public MessageFields clone() throws CloneNotSupportedException {
		MessageFields clone = new MessageFields(idGroup);
		clone.head = cloneChildren(head);
		return clone;
	}
	private Node cloneChildren(Node head) throws CloneNotSupportedException {
		Node clone = head.clone();
		Node prev = clone;
		Node node = head;
		while((node = node.next) != null) {
			prev.next = node.clone();
			if (node instanceof Parent) {
				((Parent)prev.next).head = cloneChildren(((Parent)node).head);// Solo se usara recursividad en padres
			}
			prev = prev.next;
		}
		return clone;
	}
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("[\n");
		Node node = this.head;
		while((node = node.next) != null) {
			s.append(node.toString());
		}
		s.append("]\n");
		return s.toString();
	}
	abstract class Node implements Cloneable {
		Node next;
		String idField;
		short id;
		byte[] bytes;
		Node() {}
		Node(String idField, short id, byte[] value) {
			this.idField = idField;
			this.id = id;
			this.bytes = value;
		}
		public String getValue() {
			return bytes != null ? new String(bytes) : null;
		}
		public byte[] getBytes() {
			return bytes;
		}
		abstract boolean hasChildren();
		abstract int numberOfChildren();
		abstract public Node clone() throws CloneNotSupportedException;

	}
	class Leaf extends Node {
		Leaf() {
			super();
		}
		Leaf(String idField, short id, byte[] value) {
			super(idField, id, value);
		}
		public boolean hasChildren() {
			return false;
		}
		public List<Node> getChildren() {
			return null;
		}
		public int numberOfChildren() {
			return 0;
		}
		public Leaf clone() throws CloneNotSupportedException {
			Leaf clone = new Leaf();
			if (this.bytes != null) clone.bytes = this.bytes.clone();
			clone.idField = this.idField;
			clone.id = this.id;
			//if (this.next != null) clone.next = this.next.clone(); // # NO USAMOS RECURSIVIDAD!!
			return clone;
		}
		public String toString() {
			return idField+":["+(bytes != null ? new String(bytes) : null)+"]\n";
		}
	}

	class Parent extends Node {
		Node head;
		Parent() {
			super();
			head = new Leaf();
		}
		Parent(String idField, short id, byte[] value) {
			super(idField, id, value);
			head = new Leaf();
		}
		public boolean hasChildren() {
			return head.next != null;
		}
		public List<Node> getChildren() {
			if (!hasChildren()) {
				return null;
			}
			List<Node> children = new ArrayList<>();
			Node node = this.head;
			while((node = node.next) != null) {
				children.add(node);
			}
			return children;
		}
		public int numberOfChildren() {
			int size = 0;
			Node node = head;
			while((node = node.next)!=null) {size++;}
			return size;
		}
		public Parent clone() throws CloneNotSupportedException {
			Parent clone = new Parent();
			if (this.bytes != null) clone.bytes = this.bytes.clone();
			clone.idField = this.idField;
			clone.id = this.id;
			//if (this.next != null) clone.next = this.next.clone(); // # NO USAMOS RECURSIVIDAD!!
			//if (this.head != null) clone.head = this.head.clone(); // # NO USAMOS RECURSIVIDAD!!
			return clone;
		}
		public String toString() {
			StringBuilder s = new StringBuilder();
			s.append(idField+":["+(bytes != null ? new String(bytes) : null)+"][\n");
			Node node = this.head;
			while((node = node.next) != null) {
				s.append(node.toString());
			}
			s.append("]\n");
			return s.toString();
		}
	}
}
