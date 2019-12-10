package com.fimet.commons.exception;

public class ClassLoaderException extends FimetException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public ClassLoaderException() {
		super();
	}

	public ClassLoaderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ClassLoaderException(String message, Throwable cause) {
		super(message, cause);
	}

	public ClassLoaderException(String message) {
		super(message);
	}

	public ClassLoaderException(Throwable cause) {
		super(cause);
	}

}
