package com.fimet.commons.exception;

public class ParserFormulaException extends ParserException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public ParserFormulaException() {
	}

	public ParserFormulaException(String message) {
		super(message);
	}

	public ParserFormulaException(Throwable cause) {
		super(cause);
	}

	public ParserFormulaException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParserFormulaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
