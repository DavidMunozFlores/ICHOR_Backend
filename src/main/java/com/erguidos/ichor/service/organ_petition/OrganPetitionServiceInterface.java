package com.erguidos.ichor.service.organ_petition;

import com.erguidos.ichor.dto.request.StateUpdateOrganPetitionRequest;

import java.util.Set;

import com.erguidos.ichor.dto.request.AuthenticatedRequest;
import com.erguidos.ichor.dto.request.OrganPetitionRequest;
import com.erguidos.ichor.dto.response.OrganPetitionResponse;
import com.erguidos.ichor.enums.OrganPetitionState;

public interface OrganPetitionServiceInterface {
	OrganPetitionResponse createOrganPetition(AuthenticatedRequest<OrganPetitionRequest> ar);
	OrganPetitionResponse acceptOrganPetition(AuthenticatedRequest<StateUpdateOrganPetitionRequest> ar);
	OrganPetitionResponse cancellOrganPetition(AuthenticatedRequest<StateUpdateOrganPetitionRequest> ar);
	OrganPetitionResponse checkOrganPetition(AuthenticatedRequest<StateUpdateOrganPetitionRequest> ar);
	Set<OrganPetitionResponse> findByOrganPetitionState(OrganPetitionState organPetitionState);
}
