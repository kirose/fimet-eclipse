package com.fimet.core.usecase;


import com.fimet.core.ISO8583.parser.Message;

public class AcquirerResponse implements Cloneable {
	private Message message;
	public AcquirerResponse() {
		super();
	}
	public AcquirerResponse clone() throws CloneNotSupportedException {
		return (AcquirerResponse) super.clone();
	}
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
}
