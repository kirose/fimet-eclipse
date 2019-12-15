package com.fimet.commons.exception;

public class SimulatorException extends FimetException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public SimulatorException() {
	}

	public SimulatorException(String message) {
		super(message);
	}

	public SimulatorException(Throwable cause) {
		super(cause);
	}

	public SimulatorException(String message, Throwable cause) {
		super(message, cause);
	}

	public SimulatorException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
