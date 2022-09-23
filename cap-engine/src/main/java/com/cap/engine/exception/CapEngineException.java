package com.cap.engine.exception;

public class CapEngineException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public CapEngineException() {
		super();
	}

	/**
	 * @param message
	 */
	public CapEngineException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public CapEngineException(String message, Throwable cause) {
		super(message, cause);
	}
}
