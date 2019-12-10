package com.fimet.commons.exception;

public class FieldValidatorException extends FimetException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public FieldValidatorException() {
	}

	public FieldValidatorException(String message) {
		super(message);
	}

	public FieldValidatorException(Throwable cause) {
		super(cause);
	}

	public FieldValidatorException(String message, Throwable cause) {
		super(message, cause);
	}

	public FieldValidatorException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
