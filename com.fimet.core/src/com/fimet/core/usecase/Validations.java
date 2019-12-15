package com.fimet.core.usecase;

import java.util.List;

import com.google.gson.annotations.JsonAdapter;
import com.fimet.core.json.adapter.ValidationsAdapter;

@JsonAdapter(ValidationsAdapter.class)
public class Validations implements Cloneable {
	private List<Validation> request;
	private List<Validation> response;
	private List<Validation> extract;
	public Validations() {
		super();
	}
	public List<Validation> getRequest() {
		return request;
	}
	public void setRequest(List<Validation> request) {
		this.request = request;
	}
	public List<Validation> getResponse() {
		return response;
	}
	public void setResponse(List<Validation> response) {
		this.response = response;
	}
	public List<Validation> getExtract() {
		return extract;
	}
	public void setExtract(List<Validation> extract) {
		this.extract = extract;
	}

	public Validations clone() throws CloneNotSupportedException {
		return (Validations) super.clone();
	}
}
