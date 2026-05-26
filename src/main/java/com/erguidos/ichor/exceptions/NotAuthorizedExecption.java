package com.erguidos.ichor.exceptions;

public class NotAuthorizedExecption extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public NotAuthorizedExecption(String msj) {
		super(msj);
	}
}