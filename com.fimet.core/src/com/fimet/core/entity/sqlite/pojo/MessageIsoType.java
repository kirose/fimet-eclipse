package com.fimet.core.entity.sqlite.pojo;

public enum MessageIsoType {
	ACQ_REQ((short)0,"Acqurier Request"),
	ACQ_RES((short)1,"Acqurier Response"),
	ISS_REQ((short)2,"Issuer Request"),
	ISS_RES((short)3,"Issuer Response");
	private final short id;
	private final String name;
	public static MessageIsoType get(short id) {
		return values()[(int)id];
	}
	private MessageIsoType(short id, String name) {
		this.id = id;
		this.name = name;
	}
	public short getId() {
		return id;
	}
	public String toString() {
		return name;
	}
}
