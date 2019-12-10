package com.fimet.core.usecase;

import com.fimet.core.ISO8583.parser.Message;

public class IssuerRequest implements Cloneable {
	private Message message;
	public IssuerRequest() {
		super();
	}
	public IssuerRequest clone() throws CloneNotSupportedException {
		return (IssuerRequest) super.clone();
	}
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
}
