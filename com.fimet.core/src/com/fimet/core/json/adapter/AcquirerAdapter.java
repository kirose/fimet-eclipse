package com.fimet.core.json.adapter;

import java.io.IOException;

import com.fimet.commons.exception.ParserException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.fimet.core.net.ISocket;
import com.fimet.core.usecase.Acquirer;
import com.fimet.core.usecase.AcquirerRequest;
import com.fimet.core.usecase.AcquirerResponse;

public class AcquirerAdapter extends TypeAdapter<Acquirer> {
	
	private TypeAdapter<ISocket> connectionAdapter;
	private TypeAdapter<AcquirerRequest> acquirerRequestAdapter;
	private TypeAdapter<AcquirerResponse> acquirerResponseAdapter;
	
	public AcquirerAdapter() {
		connectionAdapter = JsonAdapterFactory.GSON.getAdapter(ISocket.class);
		acquirerRequestAdapter = JsonAdapterFactory.GSON.getAdapter(AcquirerRequest.class);
		acquirerResponseAdapter = JsonAdapterFactory.GSON.getAdapter(AcquirerResponse.class);
	}
	@Override
	public Acquirer read(JsonReader in) throws IOException {
		Acquirer acquirer = new Acquirer();
		String name;
		in.beginObject();
 		while (in.hasNext() && in.peek() == JsonToken.NAME) {
			name = in.nextName();
			if ("connection".equals(name)) {
				acquirer.setConnection(connectionAdapter.read(in));
			} else if ("request".equals(name)) {
				acquirer.setRequest(acquirerRequestAdapter.read(in));
			} else if ("response".equals(name)) {
				acquirer.setResponse(acquirerResponseAdapter.read(in));
			} else {
				throw new ParserException("acquirer: Unexpected field "+in.peek());
			}
		}
		in.endObject();
		return acquirer;
	}
	@Override
	public void write(JsonWriter out, Acquirer acquirer) throws IOException {
		out.beginObject();
		if (acquirer.getConnection() != null) {
			out.name("connection");
			connectionAdapter.write(out, acquirer.getConnection());
		}
		if (acquirer.getRequest() != null && acquirer.getRequest().getMessage() != null) {
			out.name("request");
			acquirerRequestAdapter.write(out, acquirer.getRequest());
		}
		if (acquirer.getResponse() != null && acquirer.getResponse().getMessage() != null) {
			out.name("response");
			acquirerResponseAdapter.write(out, acquirer.getResponse());
		}
		out.endObject();
	}
}
