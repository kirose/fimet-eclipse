package com.fimet.core.usecase;

import com.google.gson.annotations.JsonAdapter;
import com.fimet.core.net.ISocket;
import com.fimet.core.json.adapter.AcquirerAdapter;
import com.fimet.core.json.adapter.SocketAdapter;

@JsonAdapter(AcquirerAdapter.class)
public class Acquirer implements Cloneable {

	@JsonAdapter(SocketAdapter.class)
	private ISocket connection;
	private AcquirerRequest request;
	private AcquirerResponse response;
	public Acquirer(ISocket connection, AcquirerRequest request) {
		super();
		this.connection = connection;
		this.request = request;
	}
	public Acquirer() {
		super();
		request = new AcquirerRequest();
	}
	public ISocket getConnection() {
		return connection;
	}
	public void setConnection(ISocket connection) {
		this.connection = connection;
	}
	public AcquirerRequest getRequest() {
		return request;
	}
	public void setRequest(AcquirerRequest request) {
		this.request = request;
	}
	public AcquirerResponse getResponse() {
		return response;
	}
	public void setResponse(AcquirerResponse response) {
		this.response = response;
	}
	public Acquirer clone() throws CloneNotSupportedException {
		Acquirer a =(Acquirer) super.clone();
		if (request != null)
			a.setRequest(request.clone());
		if (response != null) 
			a.setResponse(response.clone());
		return a;
	}
}
