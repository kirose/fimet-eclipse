package com.fimet.core.json.adapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fimet.commons.exception.ParserException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.fimet.core.usecase.Field;
import com.fimet.core.usecase.UseCase;

public class ListFieldAdapter extends TypeAdapter<List<Field>>{
	//private TypeAdapter<UseCase> delegateAdapter;
	public ListFieldAdapter(TypeAdapter<UseCase> delegateAdapter) {
		//this.delegateAdapter = delegateAdapter;
	}
	@Override
	public List<Field> read(JsonReader in) throws IOException {
		if (in.peek() == JsonToken.BEGIN_ARRAY) {
			return parseArray(in);
		} else if (in.peek() == JsonToken.BEGIN_OBJECT) {
			return parseObject(in);
		}
		throw new IllegalStateException("Expected a Array or Object but was " + in.peek() + in);
	}
	private List<Field> parseObject(JsonReader in) throws IOException {
		List<Field> fields = new ArrayList<>();
		String name;
		String value;
		in.beginObject();
		while (in.hasNext()) {
			name = in.nextName();
			value = in.nextString();
			if (name.length() == 0) {
				throw new ParserException("Invalid IdField for includeFields "+in);
			}
			fields.add(new Field(name, value));
		}
		in.endObject();
		return fields;
	}
	private List<Field> parseArray(JsonReader in) throws IOException {
		List<Field> fields = new ArrayList<>();
		String name;
		String value;
		in.beginArray();
		while (in.hasNext()) {
			in.beginObject();
			name = in.nextName();
			value = in.nextString();
			if (name.length() == 0) {
				throw new ParserException("Invalid IdField for includeFields "+in);
			}
			fields.add(new Field(name, value));
			in.endObject();
		}
		in.endArray();
		return fields;
	}
	@Override
	public void write(JsonWriter out, List<Field> fields) throws IOException {
		out.beginObject();
		for (Field field : fields) {
			out.name(field.getKey()).value(field.getValue());
		}
		out.endObject();
	}
}
