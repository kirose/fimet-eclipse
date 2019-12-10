package com.fimet.commons.exception;

public class StressException extends FimetException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public StressException() {
		super();
	}

	public StressException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public StressException(String message, Throwable cause) {
		super(message, cause);
	}

	public StressException(String message) {
		super(message);
	}

	public StressException(Throwable cause) {
		super(cause);
	}

}
