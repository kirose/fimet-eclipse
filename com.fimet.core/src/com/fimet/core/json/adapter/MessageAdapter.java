package com.fimet.core.json.adapter;

import java.io.IOException;
import java.util.List;

import com.fimet.commons.console.Console;
import com.fimet.commons.data.writer.impl.ByteArrayWriter;
import com.fimet.commons.exception.ParserException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.fimet.core.Activator;
import com.fimet.core.IFieldParserManager;
import com.fimet.core.IParserManager;
import com.fimet.core.Manager;
import com.fimet.core.ISO8583.adapter.IAdapterManager;
import com.fimet.core.ISO8583.parser.Field;
import com.fimet.core.ISO8583.parser.IFieldParser;
import com.fimet.core.ISO8583.parser.Message;

public class MessageAdapter extends TypeAdapter<Message>{
	private static IParserManager parserManager = Manager.get(IParserManager.class);
	private static IAdapterManager adapterManager = Manager.get(IAdapterManager.class);
	private static IFieldParserManager fieldParserManager = Manager.get(IFieldParserManager.class);
	@Override
	public Message read(JsonReader in) throws IOException {
		Message msg = new Message();
		in.beginObject();
		String name;
		 
 		while (in.hasNext() && in.peek() == JsonToken.NAME) {
			name = in.nextName();
			if ("parser".equals(name)) {
				msg.setParser(parserManager.getParser(in.nextString()));
			} else if ("header".equals(name)) {
				msg.setHeader(in.nextString());
			} else if ("mti".equals(name)) {
				msg.setMti(in.nextString());
			} else if ("adapter".equals(name)) {
				msg.setAdapter(adapterManager.getAdapter(in.nextString()));
			} else if ("fields".equals(name)) {
				if (msg.getParser() == null) {
					throw new ParserException("message.parser must be declared before message.fields");
				}
				try {
					readFields(in, msg);
				} catch (Exception e) {
					Activator.getInstance().warning("Error Parsing message",e);
				}
			} else {
				throw new ParserException("message: Unexpected field "+in.peek());
			}
		}
		in.endObject();
		if (msg.getParser() == null) {
			throw new ParserException("message.parser is required");
		}
		if (msg.getAdapter() == null) {
			throw new ParserException("message.adapter is required");
		}
		if (msg.getHeader() == null) {
			throw new ParserException("message.header is required");
		}
		if (msg.getMti() == null) {
			throw new ParserException("message.mti is required");
		}
		return msg;
	}
	private void readFields(JsonReader in, Message message) throws IOException{
		if (in.hasNext() && in.peek() == JsonToken.NULL) {
			in.nextNull();
		}
		in.beginObject();
		while (in.hasNext()) {
			readField(in, null, message);
		}
		in.endObject();
	}
	private void readField(JsonReader in, String idParent, Message message) throws IOException {
		if (!in.hasNext()) {
			return;
		}
		String key = in.nextName();
		String idField;
		if (in.hasNext()) {
			idField = idParent != null ? (idParent+"."+key) : key;
			if (in.peek() == JsonToken.STRING) {
				String value = in.nextString();
				message.setValue(idField, value);
			} else if (in.peek() == JsonToken.BEGIN_OBJECT) {
				message.setValue(idField, null);
				in.beginObject();
				while (in.hasNext() && in.peek() != JsonToken.END_OBJECT) {
					readField(in, idField, message);
				}
				formatParent(message, idField);
				in.endObject();
			}
		}
	}
	private void formatParent(Message message, String idField) {
		IFieldParser fieldParser = fieldParserManager.getFieldParser(message.getParser().getIdGroup(), idField);
		if (fieldParser == null) {
			Console.getInstance().warning(MessageAdapter.class, message.getParser()+", Unknow field "+idField);
		} else {
			try {
				ByteArrayWriter writer = new ByteArrayWriter();
				fieldParser.format(writer, message);
				message.setField(idField, writer.getBytes());
			} catch (Exception e) {
				Console.getInstance().warning(MessageAdapter.class, "Cannot format field "+idField+": "+e.getMessage());
				Activator.getInstance().warning("Cannot format field "+idField+": "+e.getMessage(), e);
			}
		}
	}
	@Override
	public void write(JsonWriter out, Message msg) throws IOException {
		 out.beginObject();
		 if (msg.getParser() != null) out.name("parser").value(msg.getParser().toString());
		 if (msg.getAdapter() != null) out.name("adapter").value(msg.getAdapter().getName());
		 if (msg.getHeader() != null) out.name("header").value(msg.getHeader());
		 if (msg.getMti() != null) out.name("mti").value(msg.getMti());
		 writeFields(out, msg);
		 out.endObject();
	}
	private void writeFields(JsonWriter out, Message message) throws IOException{
 		out.name("fields");
 		out.beginObject();
 		List<Field> roots = message.getRootFields();
 		for (Field field : roots) {
			out.name(field.getKey());
			if (field.hasChildren()) {
				formatParent(out, field);
			} else {
				out.value(field.getValue());
			}
		}
 		out.endObject();
	}
	private void formatParent(JsonWriter out, Field parent) throws IOException {
		out.beginObject();
		for (Field field : parent.getChildren()) {
			out.name(field.getKey());
			if (field.hasChildren()) {
				formatParent(out, field);
			} else {
				out.value(field.getValue());
			}			
		}
		out.endObject();
	}
}
