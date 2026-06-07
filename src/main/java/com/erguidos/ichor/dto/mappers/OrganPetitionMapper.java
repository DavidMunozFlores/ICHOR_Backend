package com.erguidos.ichor.dto.mappers;

import java.util.List;

import com.erguidos.ichor.dto.response.OrganPetitionResponse;
import com.erguidos.ichor.entity.OrganPetition;

public class OrganPetitionMapper {
	private OrganPetitionMapper() {}

	public static OrganPetitionResponse toOrganPetitionResponse(OrganPetition op) {
		return new OrganPetitionResponse(
				op.getId(),
				op.getPatient().getId(),
				op.getOrganType(),
				op.getWeightGrams(),
				op.getVolumeCC(),
				op.getHla(),
				op.getOrganPetitionState(),
				OrganMapper.toRegisterOrganResponse(op.getOrgan())
				); 
	}
	
    public static List<OrganPetitionResponse> toOrganPetitionResponse(List<OrganPetition> organPetitions) {
        if (organPetitions == null) {
            return List.of();
        }
        return organPetitions.stream().map(OrganPetitionMapper::toOrganPetitionResponse).toList();
    }

}
