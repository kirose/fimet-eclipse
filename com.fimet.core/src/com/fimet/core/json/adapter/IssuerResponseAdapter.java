package com.fimet.core.json.adapter;

import java.io.IOException;
import java.util.List;

import com.fimet.commons.exception.ParserException;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.fimet.core.usecase.Field;
import com.fimet.core.usecase.IssuerResponse;

public class IssuerResponseAdapter extends TypeAdapter<IssuerResponse> {
	
	private TypeAdapter<List<Field>> listFieldAdapter;
	private TypeAdapter<List<String>> listStringAdapter;
	
	public IssuerResponseAdapter() {
		listFieldAdapter = JsonAdapterFactory.GSON.getAdapter(new TypeToken<List<Field>>() {});
		listStringAdapter = JsonAdapterFactory.GSON.getAdapter(new TypeToken<List<String>>() {});
	}
	@Override
	public IssuerResponse read(JsonReader in) throws IOException {
		IssuerResponse ir = new IssuerResponse();
		String name;
		in.beginObject();
 		while (in.hasNext() && in.peek() == JsonToken.NAME) {
			name = in.nextName();
			if ("includeFields".equals(name)) {
				ir.setIncludeFields(listFieldAdapter.read(in));
			} else if ("excludeFields".equals(name)) {
				ir.setExcludeFields(listStringAdapter.read(in));
			} else if ("timeout".equals(name)) {
				if (in.peek() == JsonToken.BOOLEAN && in.nextBoolean()) {
					ir.setTimeout(-1);
				} else if (in.peek() == JsonToken.NUMBER) {
					int delay = (int)in.nextLong();
					ir.setTimeout(delay > 0 ? delay : -1);
				} else {
					throw new ParserException("message.parser must be declared before message.fields");
				}
			} else {
				throw new ParserException("issuer.request: Unexpected token "+in.peek()+"");
			}
		}
		in.endObject();
		return ir;
	}
	@Override
	public void write(JsonWriter out, IssuerResponse ir) throws IOException {
		out.beginObject();
		if (ir.getIncludeFields() != null) {
			out.name("includeFields");
			listFieldAdapter.write(out, ir.getIncludeFields());
		}
		if (ir.getExcludeFields() != null) {
			out.name("excludeFields");
			listStringAdapter.write(out, ir.getExcludeFields());
		}
		if (ir.getTimeout() != null) {
			out.name("timeout");
			if (ir.getTimeout() > 0) {
				out.value(ir.getTimeout());	
			} else {
				out.value(Boolean.TRUE);
			}
		}
		out.endObject();
	}
}
