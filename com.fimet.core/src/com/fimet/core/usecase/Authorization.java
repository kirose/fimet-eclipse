package com.fimet.core.usecase;

import java.util.List;

import com.google.gson.annotations.JsonAdapter;
import com.fimet.core.ISO8583.parser.Message;
import com.fimet.core.json.adapter.AuthorizationAdapter;
import com.fimet.core.json.adapter.MessageAdapter;

@JsonAdapter(AuthorizationAdapter.class)
public class Authorization implements Cloneable {
	private String header;
	private String mti;
	private List<Field> includeFields;
	private List<String> excludeFields;
	private List<String> replaceFields;
	private boolean isBoolean;
	@JsonAdapter(MessageAdapter.class)
	private Message message;
	public Authorization(boolean is) {
		super();
		isBoolean = is;
	}
	public Authorization() {
		super();
	}
	public List<Field> getIncludeFields() {
		return includeFields;
	}
	public void setIncludeFields(List<Field> includeFields) {
		this.includeFields = includeFields;
	}
	public List<String> getExcludeFields() {
		return excludeFields;
	}
	public void setExcludeFields(List<String> excludeFields) {
		this.excludeFields = excludeFields;
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
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	public List<String> getReplaceFields() {
		return replaceFields;
	}
	public void setReplaceFields(List<String> replaceFields) {
		this.replaceFields = replaceFields;
	}
	public boolean isBoolean() {
		return isBoolean;
	}
	public Authorization clone() throws CloneNotSupportedException {
		Authorization a = (Authorization) super.clone();
		if (message != null)
			a.setMessage(message.clone());
		return a;
	}
	
}
