package com.fimet.commons.exception;

public class AdapterException extends FimetException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public AdapterException() {
		super();
	}

	public AdapterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AdapterException(String message, Throwable cause) {
		super(message, cause);
	}

	public AdapterException(String message) {
		super(message);
	}

	public AdapterException(Throwable cause) {
		super(cause);
	}

}
