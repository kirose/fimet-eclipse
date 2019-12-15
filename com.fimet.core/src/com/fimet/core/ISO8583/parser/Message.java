package com.fimet.core.ISO8583.parser;

import com.fimet.commons.exception.ParserException;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.fimet.core.ISO8583.adapter.IAdapter;
import com.fimet.core.json.adapter.JsonAdapterFactory;
import com.fimet.core.json.adapter.MessageAdapter;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * The message of the transaction
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
@JsonAdapter(MessageAdapter.class)
public class Message implements Serializable, Cloneable, IMessage {
	/**
	 * serialVersionUID
	 */
	
	private static final long serialVersionUID = 1L;
	private static final byte[] EMPTY = new byte[0];
	
	@Expose	protected IParser parser;
	@Expose	protected IAdapter adapter;
	@Expose protected String header;
	@Expose protected String mti;
	protected MessageFields fields;

	public Message() {
		fields = new MessageFields(this);
	}
	public IAdapter getAdapter() {
		return adapter;
	}
	public void setAdapter(IAdapter adapter) {
		this.adapter = adapter;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getMti() {
		return mti;
	}
	public void setMti(String mti) {
		this.mti = mti;
	}
	public IParser getParser() {
		return parser;
	}
	public void setParser(IParser parser) {
		this.parser = parser;
		if (parser != null) {
			fields.idGroup = parser.getIdGroup();
		}
	}
	public List<Field> getRootFields() {
		return new FieldTree(fields).getRoots();
	}
	public String toJson() {
		return JsonAdapterFactory.GSON.toJson(this);
	}
	@Override
	public String toString() {
		return toJson();
	}
	public boolean hasField(int idField) {
		return fields.find(idField+"") != null;
	}
	public boolean hasField(String idField) {
		return fields.find(idField) != null;
	}
	public byte[] getField(int idField) {
		return fields.get(idField+"");
	}
	public byte[] getField(String idField) {
		return fields.get(idField);
	}
	public void remove(String idField) {
		fields.remove(idField);
	}
	public void removeAll(Collection<String> all) {
		for (String idField : all) {
			fields.remove(idField);
		}
	}
	public void replace(String idField, String value) {
		fields.replace(idField, value != null ? value.getBytes() : null);
	}
	public void replace(String idField, byte[] value) {
		fields.replace(idField, value);
	}
	public String getValue(String idField) {
		byte[] bs = fields.get(idField);
		return bs != null ? new String(bs) : null;
	}
	public String getValue(int idField) {
		byte[] bs = fields.get(""+idField);
		return bs != null ? new String(bs) : null;
	}
	public void setValue(String idField, String value) {
		setField(idField, value == null ? EMPTY : value.getBytes());
	}
	public void setField(String idField, byte[] value) {
		fields.insert(idField, value);
	}
	public Message clone(List<String> excludeFields) {
		Message msg;
		try {
			msg = this.clone();
		} catch (CloneNotSupportedException e) {
			throw new ParserException("Cannot clone message",e);
		}
		if (excludeFields != null) {
			for (String idField : excludeFields) {
				msg.remove(idField);
			}
		}
		msg.setParser(parser);
		msg.setAdapter(adapter);
		msg.setHeader(header);
		msg.setMti(mti);
		return msg;
	}
	public Message clone() throws CloneNotSupportedException {
		Message clone = (Message) super.clone();
		if (fields != null) {
			clone.fields = this.fields.clone();
		}
		return clone; 
	}
	public int[] getBitmap() {
		return fields.getBitmap();
	}
	public String getPan() {
		if (hasField(35)) {// Nacional
			return getValue(35).substring(0,16);
		} else if (hasField(2)) {// Visa MasterCard
			return getValue(2).substring(0,16);
		} else if (hasField("63.EZ.10.track2")) {// Int
			return getValue("63.EZ.10.track2").substring(0,16);
		} else if (hasField("63.EZ.9.track2")) {// TPV
			return getValue("63.EZ.9.track2").substring(0,16);
		} else {
			return null;
		}
	}
	public boolean hasChildren(String idField) {
		return fields.hasChildren(idField);
	}
	public List<String> getIdChildren() {
		return fields.getIdChildren();
	}
	public List<String> getIdChildren(String idField) {
		return fields.getIdChildren(idField);
	}
}
