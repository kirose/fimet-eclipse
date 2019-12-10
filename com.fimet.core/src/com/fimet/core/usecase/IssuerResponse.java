package com.fimet.core.usecase;

import java.util.List;

import com.fimet.commons.console.Console;
import com.google.gson.annotations.JsonAdapter;
import com.fimet.core.ISO8583.parser.Message;
import com.fimet.core.json.adapter.IssuerResponseAdapter;

@JsonAdapter(IssuerResponseAdapter.class)
public class IssuerResponse implements Cloneable {
	private Integer timeout;
	private Message message;
	private List<Field> includeFields;
	private List<String> excludeFields;
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
	public void includeFields(Message response) {
		if (includeFields != null && !includeFields.isEmpty()) {
			Console.getInstance().info(IssuerResponse.class,"Including response fields");
			for (Field field : includeFields) {
				response.setValue(field.getKey(), field.getValue());
			}
		}
	}
	public void excludeFields(Message response) {
		if (excludeFields != null && !excludeFields.isEmpty()) {
			Console.getInstance().info(IssuerResponse.class,"Excluding response fields");
			for (String idField : excludeFields) {
				response.remove(idField);
			}
		}
	}
	public IssuerResponse clone() throws CloneNotSupportedException {
		return (IssuerResponse) super.clone();
	}
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	public Integer getTimeout() {
		return timeout;
	}
	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}
}
