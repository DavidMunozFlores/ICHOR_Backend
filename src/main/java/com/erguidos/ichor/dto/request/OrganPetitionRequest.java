package com.erguidos.ichor.dto.request;

import com.erguidos.ichor.enums.OrganType;

public record OrganPetitionRequest(
		Long idPatient,
		OrganType organType,
		Double weightGrams,
		Double volumeCC,
		String hla
		) implements DataRequestInterface {}
