package com.fimet.core.net;

import com.fimet.core.IMachine;

public class Machine implements IMachine {

	private String name; // Machine HOSTNAME
	private String address; // IP Adress
	public Machine() {
		super();
	}
	public Machine(String name, String address) {
		super();
		this.name = name;
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
