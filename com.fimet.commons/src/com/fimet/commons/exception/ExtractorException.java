package com.fimet.commons.exception;

public class ExtractorException extends FimetException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public ExtractorException() {
	}

	public ExtractorException(String message) {
		super(message);
	}

	public ExtractorException(Throwable cause) {
		super(cause);
	}

	public ExtractorException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExtractorException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
