package com.fimet.commons.exception;

public class FimetIOException extends FimetException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public FimetIOException() {
	}

	public FimetIOException(String message) {
		super(message);
	}

	public FimetIOException(Throwable cause) {
		super(cause);
	}

	public FimetIOException(String message, Throwable cause) {
		super(message, cause);
	}

	public FimetIOException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
