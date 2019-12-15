package com.fimet.commons.exception;

public class FormatException extends FimetException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public FormatException() {
		super();
	}

	public FormatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FormatException(String message, Throwable cause) {
		super(message, cause);
	}

	public FormatException(String message) {
		super(message);
	}

	public FormatException(Throwable cause) {
		super(cause);
	}

}
