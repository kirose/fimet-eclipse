package com.fimet.core.entity.sqlite.pojo;

public class Notice {
	public static final short INFO = 0;
	public static final short WARNING = 1;
	public static final short ERROR = 2;

	private short severity;
	private String error;
	
	public Notice(short severity, String error) {
		super();
		this.severity = severity;
		this.error = error;
	}
	public short getSeverity() {
		return severity;
	}
	public void setSeverity(short severity) {
		this.severity = severity;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
}
