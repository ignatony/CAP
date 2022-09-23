/**
 * 
 */
package com.cap.api.exception;

/**
 * @author Ignatious
 *
 */
public class CapAPIException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public CapAPIException() {
		super();
	}

	/**
	 * @param message
	 */
	public CapAPIException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public CapAPIException(String message, Throwable cause) {
		super(message, cause);
	}

}
