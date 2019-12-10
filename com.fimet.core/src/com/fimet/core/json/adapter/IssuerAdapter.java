package com.fimet.core.json.adapter;

import java.io.IOException;

import com.fimet.commons.exception.ParserException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.fimet.core.net.ISocket;
import com.fimet.core.usecase.Issuer;
import com.fimet.core.usecase.IssuerRequest;
import com.fimet.core.usecase.IssuerResponse;

public class IssuerAdapter extends TypeAdapter<Issuer> {
	
	private TypeAdapter<ISocket> connectionAdapter;
	private TypeAdapter<IssuerRequest> issuerRequestAdapter;
	private TypeAdapter<IssuerResponse> issuerResponseAdapter;
	
	public IssuerAdapter() {
		connectionAdapter = JsonAdapterFactory.GSON.getAdapter(ISocket.class);
		issuerRequestAdapter = JsonAdapterFactory.GSON.getAdapter(IssuerRequest.class);
		issuerResponseAdapter = JsonAdapterFactory.GSON.getAdapter(IssuerResponse.class);
	}
	@Override
	public Issuer read(JsonReader in) throws IOException {
		Issuer issuer = new Issuer();
		String name;
		in.beginObject();
 		while (in.hasNext() && in.peek() == JsonToken.NAME) {
			name = in.nextName();
			if ("connection".equals(name)) {
				issuer.setConnection(connectionAdapter.read(in));
			} else if ("request".equals(name)) {
				issuer.setRequest(issuerRequestAdapter.read(in));
			} else if ("response".equals(name)) {
				issuer.setResponse(issuerResponseAdapter.read(in));
			} else if ("connect".equals(name)) {
				issuer.setConnect(in.nextBoolean());
			} else {
				throw new ParserException("issuer: Unexpected field "+in.peek());
			}
		}
		in.endObject();
		return issuer;
	}
	@Override
	public void write(JsonWriter out, Issuer issuer) throws IOException {
		out.beginObject();
		if (issuer.getConnection() != null) {
			out.name("connection");
			connectionAdapter.write(out, issuer.getConnection());
		}
		if (issuer.getRequest() != null && issuer.getRequest().getMessage() != null) {
			out.name("request");
			issuerRequestAdapter.write(out, issuer.getRequest());
		}
		if (issuer.getResponse() != null && (
				issuer.getResponse().getExcludeFields() != null ||
				issuer.getResponse().getIncludeFields() != null ||
				issuer.getResponse().getTimeout() != null ||
				issuer.getResponse().getMessage() != null
			)
		) {
			out.name("response");
			issuerResponseAdapter.write(out, issuer.getResponse());
		}
		if (!issuer.getConnect()) out.name("connect").value(false);
		out.endObject();
	}
}
