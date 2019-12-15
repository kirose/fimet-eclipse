package com.fimet.commons.exception;

public class FimetException extends RuntimeException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public FimetException() {
	}

	public FimetException(String message) {
		super(message);
	}
	public FimetException(Throwable cause) {
		super(cause);
	}

	public FimetException(String message, Throwable cause) {
		super(message, cause);
	}

	public FimetException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
