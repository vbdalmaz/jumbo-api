package com.jumbo.api.exception;

public class StoreInvalidException extends RuntimeException {

	private static final long serialVersionUID = 2019020801L;

	public StoreInvalidException(String message) {
		super(message);
	}
}

