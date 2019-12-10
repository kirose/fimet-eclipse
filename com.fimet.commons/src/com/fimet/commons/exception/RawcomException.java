package com.fimet.commons.exception;

public class RawcomException extends FimetException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public RawcomException() {
		super();
	}

	public RawcomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RawcomException(String message, Throwable cause) {
		super(message, cause);
	}

	public RawcomException(String message) {
		super(message);
	}

	public RawcomException(Throwable cause) {
		super(cause);
	}

}
