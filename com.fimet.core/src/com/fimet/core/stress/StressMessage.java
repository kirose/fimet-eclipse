package com.fimet.core.stress;

import java.util.List;
import java.util.Map;

public class StressMessage {
	private String message;
	private Map<String,List<String>> variation;
	public StressMessage() {
		super();
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Map<String, List<String>> getVariation() {
		return variation;
	}
	public void setVariation(Map<String, List<String>> variation) {
		this.variation = variation;
	}
}
