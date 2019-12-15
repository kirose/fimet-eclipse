package com.fimet.commons.exception;

public class LogAnalyzerException extends FimetException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public LogAnalyzerException() {
	}

	public LogAnalyzerException(String message) {
		super(message);
	}

	public LogAnalyzerException(Throwable cause) {
		super(cause);
	}

	public LogAnalyzerException(String message, Throwable cause) {
		super(message, cause);
	}

	public LogAnalyzerException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
