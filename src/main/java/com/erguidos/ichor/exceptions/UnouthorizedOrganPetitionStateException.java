package com.erguidos.ichor.exceptions;

public class UnouthorizedOrganPetitionStateException  extends IllegalArgumentException{

	private static final long serialVersionUID = 1L;

	public UnouthorizedOrganPetitionStateException(String msj) {
		super(msj);
	}
}
