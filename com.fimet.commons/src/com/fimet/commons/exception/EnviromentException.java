package com.fimet.commons.exception;

public class EnviromentException extends FimetException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public EnviromentException() {
	}

	public EnviromentException(String message) {
		super(message);
	}

	public EnviromentException(Throwable cause) {
		super(cause);
	}

	public EnviromentException(String message, Throwable cause) {
		super(message, cause);
	}

	public EnviromentException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
