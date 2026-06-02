package com.erguidos.ichor.dto.response;

import java.util.List;

import com.erguidos.ichor.dto.request.Gene;
import com.erguidos.ichor.enums.BloodType;
import com.erguidos.ichor.enums.OrganType;

public record RegisterOrganResponse(
		OrganType organType,
		Double weightGrams,
		Double volumeCC,
		List<Gene> hla,
		BloodType bloodType
		) {}