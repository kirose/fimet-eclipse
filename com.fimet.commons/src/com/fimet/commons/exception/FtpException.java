package com.fimet.commons.exception;

public class FtpException extends FimetException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public FtpException() {
	}

	public FtpException(String message) {
		super(message);
	}

	public FtpException(Throwable cause) {
		super(cause);
	}

	public FtpException(String message, Throwable cause) {
		super(message, cause);
	}

	public FtpException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
