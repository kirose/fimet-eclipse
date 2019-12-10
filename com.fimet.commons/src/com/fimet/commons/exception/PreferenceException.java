package com.fimet.commons.exception;

public class PreferenceException extends FimetException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public PreferenceException() {
	}

	public PreferenceException(String message) {
		super(message);
	}

	public PreferenceException(Throwable cause) {
		super(cause);
	}

	public PreferenceException(String message, Throwable cause) {
		super(message, cause);
	}

	public PreferenceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
