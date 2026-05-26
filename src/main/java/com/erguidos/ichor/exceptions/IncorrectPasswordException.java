package com.erguidos.ichor.exceptions;

public class IncorrectPasswordException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public IncorrectPasswordException(String msj) {
		super(msj);
	}
}
