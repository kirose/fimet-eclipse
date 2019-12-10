package com.fimet.core.json.adapter;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.fimet.core.usecase.Validation;

public class ValidationAdapter extends TypeAdapter<Validation>{

	public ValidationAdapter() {
	}
	@Override
	public Validation read(JsonReader in) throws IOException {
		Validation v = new Validation();
		in.beginObject();
		String name;
 		while (in.hasNext() && in.peek() == JsonToken.NAME) {
			name = in.nextName();
			if ("name".equals(name)) {
				v.setName(in.nextString());
			} else if ("expression".equals(name)) {
				v.setExpression(in.nextString());
			} else if ("expected".equals(name)) {
				v.setExpected(nextObject(in));
			} else if ("operator".equals(name)) {
				v.setName(in.nextString());
			}
		}
 		in.endObject();
 		return v;
	}
	@Override
	public void write(JsonWriter out, com.fimet.core.usecase.Validation v) throws IOException {
		out.beginObject();
		out.name("name").value(v.getName());
		if (!"==".equals(v.getOperator())) {
			out.name("operator").value(v.getOperator());
		}
		out.name("expression").value(v.getExpression());
		if (v.getExpected() instanceof Number) {
			out.name("expected").value(((Number)v.getExpected()).doubleValue());
		} else if (v.getExpected() instanceof Boolean) {
			out.name("expected").value(((Boolean)v.getExpected()));
		} else {
			out.name("expected").value(v.getExpected()+"");
		}
		out.endObject();
	}
	private Object nextObject(JsonReader in) throws IOException {
		if (in.peek() == JsonToken.STRING) {
			return in.nextString();
		} else if (in.peek() == JsonToken.NUMBER) {
			return in.nextDouble();
		} else if (in.peek() == JsonToken.BOOLEAN) {
			return in.nextBoolean();
		} else {
			in.skipValue();
			return null;
		}
	}
}
