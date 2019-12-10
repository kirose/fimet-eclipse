package com.fimet.core.json.adapter;

import java.io.IOException;
import java.util.List;

import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.fimet.core.usecase.Validation;
import com.fimet.core.usecase.Validations;

public class ValidationsAdapter extends TypeAdapter<Validations>{

	private TypeAdapter<List<Validation>> validationListAdapter;
	public ValidationsAdapter() {
		validationListAdapter = JsonAdapterFactory.GSON.getAdapter(new TypeToken<List<Validation>>(){});
	}
	@Override
	public Validations read(JsonReader in) throws IOException {
		Validations v = new Validations();
		in.beginObject();
		String name;
 		while (in.hasNext() && in.peek() == JsonToken.NAME) {
			name = in.nextName();
			if ("request".equals(name)) {
				v.setRequest(validationListAdapter.read(in));
			} else if ("response".equals(name)) {
				v.setResponse(validationListAdapter.read(in));
			} else if ("extract".equals(name)) {
				v.setExtract(validationListAdapter.read(in));
			}
		}
 		in.endObject();
 		return v;
	}
	@Override
	public void write(JsonWriter out, com.fimet.core.usecase.Validations v) throws IOException {
		out.beginObject();
		if (v.getRequest() != null && !v.getRequest().isEmpty()) writeValidations(out, "request", v.getRequest());
		if (v.getResponse() != null && !v.getResponse().isEmpty()) writeValidations(out, "response", v.getResponse());
		if (v.getExtract() != null && !v.getExtract().isEmpty()) writeValidations(out, "extract", v.getExtract());
		out.endObject();
	}
	private void writeValidations(JsonWriter out, String name, List<Validation> request) throws IOException {
		out.name(name);
		out.beginArray();
		for (Validation v : request) {
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
		out.endArray();
	}
}
