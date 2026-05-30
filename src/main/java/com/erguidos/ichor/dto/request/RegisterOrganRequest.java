package com.erguidos.ichor.dto.request;

import com.erguidos.ichor.enums.OrganType;

public record RegisterOrganRequest(
		OrganType organType,
		Double weightGrams,
		Double volumeCC,
		String hla
		) implements DataRequestInterface {}

