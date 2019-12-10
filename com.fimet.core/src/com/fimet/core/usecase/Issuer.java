package com.fimet.core.usecase;

import com.google.gson.annotations.JsonAdapter;
import com.fimet.core.json.adapter.IssuerAdapter;
import com.fimet.core.net.ISocket;

@JsonAdapter(IssuerAdapter.class)
public class Issuer implements Cloneable {

	//@JsonAdapter(com.fimet.core.json.adapter.SourceConnectionAdapter.SourceConnectionAdapter.class)
	private ISocket connection;
	private boolean connect = true;
	private IssuerResponse response;
	private IssuerRequest request;
	public Issuer(ISocket connection) {
		super();
		this.connection = connection;
	}
	public Issuer() {
		super();
	}
	public ISocket getConnection() {
		return connection;
	}
	public void setConnection(ISocket connection) {
		this.connection = connection;
	}
	public IssuerResponse getResponse() {
		return response;
	}
	public void setResponse(IssuerResponse response) {
		this.response = response;
	}
	public IssuerRequest getRequest() {
		return request;
	}
	public void setRequest(IssuerRequest request) {
		this.request = request;
	}
	public Issuer clone() throws CloneNotSupportedException {
		Issuer i =(Issuer) super.clone();
		if (request != null)
			i.setRequest(request.clone());
		if (response != null) 
			i.setResponse(response.clone());
		return i;
	}
	public boolean getConnect() {
		return connect;
	}
	public void setConnect(boolean connect) {
		this.connect = connect;
	}
}
