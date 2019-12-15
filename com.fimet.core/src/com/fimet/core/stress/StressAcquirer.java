package com.fimet.core.stress;

import java.util.List;

import com.fimet.core.net.ISocket;

public class StressAcquirer {
	private ISocket connection;
	private List<StressMessage> messages;
	public StressAcquirer() {
		super();
	}
	public StressAcquirer(ISocket connection) {
		super();
		this.connection = connection;
	}
	public ISocket getConnection() {
		return connection;
	}
	public void setConnection(ISocket connection) {
		this.connection = connection;
	}
	public List<StressMessage> getMessages() {
		return messages;
	}
	public void setMessages(List<StressMessage> messages) {
		this.messages = messages;
	}
}
