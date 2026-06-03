package com.erguidos.ichor.dto.mappers;

import com.erguidos.ichor.dto.response.OrganPetitionResponse;
import com.erguidos.ichor.entity.OrganPetition;

public class OrganPetitionMapper {
	private OrganPetitionMapper() {}

	public static OrganPetitionResponse toOrganPetitionResponse(OrganPetition op) {
		return new OrganPetitionResponse(
				op.getPatient().getId(),
				op.getOrganType(),
				op.getWeightGrams(),
				op.getVolumeCC(),
				op.getHla(),
				op.getOrganPetitionState(),
				OrganMapper.toRregisterOrganResponse(op.getOrgan())
				); 
	}

}
