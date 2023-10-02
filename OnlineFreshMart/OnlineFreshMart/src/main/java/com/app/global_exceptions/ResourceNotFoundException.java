package com.app.global_exceptions;

public class ResourceNotFoundException extends Exception {
	public ResourceNotFoundException(String mesg) {
		super(mesg);
	}
}
