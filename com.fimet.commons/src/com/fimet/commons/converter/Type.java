package com.fimet.commons.converter;

public enum Type {

	NONE(0,"None"),
	ASCII(1,"Ascii"),	
	EBCDIC(2,"Ebcdic"),	
	HEX(3,"Hex"),
	HEXEBCDIC(4,"HexEbcdic"),
	BINARY(5,"Binary");
	
	int id;
	String name;
	Type(int id, String name) {
		this.id = id;
	    this.name = name;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	@Override
	public String toString() {
		return name;
	}
}
