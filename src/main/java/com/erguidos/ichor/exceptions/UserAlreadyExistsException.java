package com.erguidos.ichor.exceptions;

public class UserAlreadyExistsException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public UserAlreadyExistsException(String msj) {
		super(msj);
	}
}
