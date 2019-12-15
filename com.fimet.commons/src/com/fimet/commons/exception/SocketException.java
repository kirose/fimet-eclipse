package com.fimet.commons.exception;
/**
 * 
 */

/**
 * @author Marco Salazar
 *
 */
public class SocketException extends FimetException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public SocketException() {
	}

	/**
	 * @param message
	 */
	public SocketException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public SocketException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public SocketException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public SocketException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
