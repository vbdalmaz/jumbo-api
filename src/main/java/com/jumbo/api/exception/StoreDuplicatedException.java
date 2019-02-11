package com.jumbo.api.exception;

public class StoreDuplicatedException extends RuntimeException {

	private static final long serialVersionUID = 2019020801L;

	public StoreDuplicatedException(String message) {
			super(message);
		}
}
