package com.erguidos.ichor.exceptions;

public class UserNotFoundException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String msj) {
		super(msj);
	}
}
