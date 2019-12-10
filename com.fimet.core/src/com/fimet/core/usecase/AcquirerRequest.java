package com.fimet.core.usecase;

import com.google.gson.annotations.JsonAdapter;
import com.fimet.core.ISO8583.parser.Message;
import com.fimet.core.json.adapter.AuthorizationAdapter;
import com.fimet.core.json.adapter.MessageAdapter;

public class AcquirerRequest implements Cloneable {
	@JsonAdapter(AuthorizationAdapter.class)
	private Authorization authorization;
	@JsonAdapter(MessageAdapter.class)
	private Message message;
	public AcquirerRequest(Message message) {
		super();
		this.message = message;
	}
	public AcquirerRequest() {
		super();
	}
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	public Authorization getAuthorization() {
		return authorization;
	}
	public void setAuthorization(Authorization authorization) {
		this.authorization = authorization;
	}
	public AcquirerRequest clone() throws CloneNotSupportedException {
		AcquirerRequest r = (AcquirerRequest) super.clone();
		if (authorization != null)
			r.setAuthorization(authorization.clone());
		if (message != null)
			r.setMessage(message.clone());
		return r;
	}
}
