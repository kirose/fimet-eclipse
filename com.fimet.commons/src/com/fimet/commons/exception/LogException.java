package com.fimet.commons.exception;

public class LogException extends FimetException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3361865657292018150L;

	public LogException() {
		super();
	}

	public LogException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public LogException(String message, Throwable cause) {
		super(message, cause);
	}

	public LogException(String message) {
		super(message);
	}

	public LogException(Throwable cause) {
		super(cause);
	}

}
