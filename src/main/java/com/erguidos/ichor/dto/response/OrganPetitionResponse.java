package com.erguidos.ichor.dto.response;

import java.util.List;

import com.erguidos.ichor.dto.request.DataRequestInterface;
import com.erguidos.ichor.dto.request.Gene;
import com.erguidos.ichor.entity.Organ;
import com.erguidos.ichor.enums.OrganPetitionState;
import com.erguidos.ichor.enums.OrganType;

public record OrganPetitionResponse (
		Long idPatient,
		OrganType organType,
		Double weightGrams,
		Double volumeCC,
		List<Gene> hla,
		OrganPetitionState petitionState,
		Organ organAssigned
		) implements DataRequestInterface {}
