package com.fimet.core.json.adapter;

import java.io.IOException;
import java.util.List;

import com.fimet.commons.exception.ParserException;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.fimet.core.ISO8583.parser.Message;
import com.fimet.core.usecase.Authorization;
import com.fimet.core.usecase.Field;

public class AuthorizationAdapter extends TypeAdapter<Authorization>{

	private TypeAdapter<List<Field>> listFieldAdapter;
	private TypeAdapter<List<String>> listStringAdapter;
	private TypeAdapter<Message> mesageAdapter;
	public AuthorizationAdapter() {
		listFieldAdapter = JsonAdapterFactory.GSON.getAdapter(new TypeToken<List<Field>>() {});
		listStringAdapter = JsonAdapterFactory.GSON.getAdapter(new TypeToken<List<String>>() {});
		mesageAdapter = JsonAdapterFactory.GSON.getAdapter(new TypeToken<Message>() {});
	}
	@Override
	public Authorization read(JsonReader in) throws IOException {
		if (in.peek() == JsonToken.BOOLEAN) {
			if (in.nextBoolean()) {
				return new Authorization(true);
			} else {
				return null;
			}
		} else if (in.peek() == JsonToken.BEGIN_OBJECT) {
			Authorization auth = new Authorization();
			in.beginObject();
			String name;
	 		while (in.hasNext() && in.peek() == JsonToken.NAME) {
				name = in.nextName();
				if ("mti".equals(name)) {
					auth.setMti(in.nextString());
				} else if ("header".equals(name)) {
					auth.setHeader(in.nextString());
				} else if ("includeFields".equals(name)) {
					auth.setIncludeFields(listFieldAdapter.read(in));
				} else if ("excludeFields".equals(name)) {
					auth.setExcludeFields(listStringAdapter.read(in));
				} else if ("replaceFields".equals(name)) {
					auth.setReplaceFields(listStringAdapter.read(in));
				} else if ("message".equals(name)) {
					auth.setMessage(mesageAdapter.read(in));
				} else {
					throw new ParserException("acquirer.request.authorization: Unexpected field "+in.peek());
				}
			}
			in.endObject();
			return auth;
		}
		throw new IllegalStateException("Expected a Boolean or Object but was " + in.peek() +" "+ in);
	}
	@Override
	public void write(JsonWriter out, Authorization auth) throws IOException {
		 if (auth.getMti() == null && auth.getHeader() == null && auth.getIncludeFields() == null && auth.getExcludeFields() == null && auth.getMessage() == null) {
			 out.value(true);
			 return;
		 }
		 out.beginObject();
		 if (auth.getMessage() != null) {
			 out.name("message");
			 mesageAdapter.write(out, auth.getMessage());
		 } else {
			 if (auth.getMti() != null) {
				 out.name("mti").value(auth.getMti());
			 }
			 if (auth.getHeader() != null) {
				 out.name("header").value(auth.getHeader());
			 }
			 if (auth.getIncludeFields() != null) {
				 out.name("includeFields");
				 listFieldAdapter.write(out, auth.getIncludeFields());
			 }
			 if (auth.getExcludeFields() != null) {
				 out.name("excludeFields");
				 listStringAdapter.write(out, auth.getExcludeFields());
			 }
			 if (auth.getExcludeFields() != null) {
				 out.name("replaceFields");
				 listStringAdapter.write(out, auth.getReplaceFields());
			 }			 
		 }
	 	 out.endObject();		
	}
}
