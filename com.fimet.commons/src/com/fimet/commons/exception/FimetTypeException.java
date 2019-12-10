package com.fimet.commons.exception;

public class FimetTypeException extends FimetException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public FimetTypeException() {
		super();
	}

	public FimetTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FimetTypeException(String message, Throwable cause) {
		super(message, cause);
	}

	public FimetTypeException(String message) {
		super(message);
	}

	public FimetTypeException(Throwable cause) {
		super(cause);
	}

}
