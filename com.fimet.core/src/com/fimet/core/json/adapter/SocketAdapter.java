package com.fimet.core.json.adapter;

import java.io.IOException;

import com.fimet.commons.exception.ParserException;
//import com.fimet.commons.exception.ParserException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.fimet.core.ISocketManager;
import com.fimet.core.Manager;
import com.fimet.core.net.ISocket;
import com.fimet.core.usecase.UseCase;

public class SocketAdapter extends TypeAdapter<ISocket>{

	protected final TypeAdapter<UseCase> delegate;
	public SocketAdapter(TypeAdapter<UseCase> delegate) {
		if (delegate == null) {
			throw new NullPointerException("TypeAdapter delegate is null");
		}
		this.delegate = delegate;
	}
	@Override
	public ISocket read(JsonReader in) throws IOException {
		
		String name = null;
		String address = null;
		Integer port = null;
		in.beginObject();
		while (in.hasNext() && in.peek() == JsonToken.NAME) {
			String key = in.nextName();
			if ("name".equals(key)) {
				name = in.nextString();
			} else if ("address".equals(key)) {
				address = in.nextString();
			} else if ("port".equals(key)) {
				port = in.nextInt();
			} else {
				throw new ParserException("socket: Unexpected field "+in.peek());
			}
		}
		in.endObject();
		if (name == null || address == null || port == null)  {
			return null;
		}
		/*if (name == null) {
			throw new ParserException("The name of source connection is null");			
		}
		if (address == null) {
			throw new ParserException("The machine address of source connection "+name+" is null");
		}
		if (port == null) {
			throw new ParserException("The socket port of source connection "+name+","+address+" is null");
		}*/
		ISocketManager socketManager = Manager.get(ISocketManager.class);
		return socketManager.find(name, address, port);
	}
	@Override
	public void write(JsonWriter out, ISocket sc) throws IOException {
		out.beginObject()
			.name("name").value(sc.getName())
	 		.name("address").value(sc.getAddress())
	 		.name("port").value(sc.getPort())
		.endObject();
	}
}
