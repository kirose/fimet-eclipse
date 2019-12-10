package com.fimet.commons.exception;

import com.fimet.commons.exception.FimetException;

public class UseCaseException extends FimetException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	public UseCaseException() {
		super();
	}
	public UseCaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	public UseCaseException(String message, Throwable cause) {
		super(message, cause);
	}
	public UseCaseException(String message) {
		super(message);
	}
	public UseCaseException(Throwable cause) {
		super(cause);
	}

}
