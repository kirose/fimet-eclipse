package com.fimet.commons.exception;
/**
 * 
 */

/**
 * @author Marco Salazar
 *
 */
public class SocketOccupiedException extends FimetException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public SocketOccupiedException() {
	}

	/**
	 * @param message
	 */
	public SocketOccupiedException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public SocketOccupiedException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public SocketOccupiedException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public SocketOccupiedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
