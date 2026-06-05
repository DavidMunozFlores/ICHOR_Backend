package com.erguidos.ichor.service.organ_petition;

import com.erguidos.ichor.dto.request.IdOrganPetitionRequest;

import java.util.Set;

import com.erguidos.ichor.dto.request.AuthenticatedRequest;
import com.erguidos.ichor.dto.request.OrganPetitionRequest;
import com.erguidos.ichor.dto.request.OrganPetitionUpdateRequest;
import com.erguidos.ichor.dto.response.OPDeleted;
import com.erguidos.ichor.dto.response.OrganPetitionResponse;
import com.erguidos.ichor.enums.OrganPetitionState;

public interface OrganPetitionServiceInterface {
	OrganPetitionResponse createOrganPetition(AuthenticatedRequest<OrganPetitionRequest> ar);
	
	OrganPetitionResponse updateOrganPetition(AuthenticatedRequest<OrganPetitionUpdateRequest> ar);
	
	OPDeleted deleteOrganPetition(AuthenticatedRequest<IdOrganPetitionRequest> ar);
	
	OrganPetitionResponse acceptOrganPetition(AuthenticatedRequest<IdOrganPetitionRequest> ar);
	
	OrganPetitionResponse cancelOrganPetition(AuthenticatedRequest<IdOrganPetitionRequest> ar);
	
	OrganPetitionResponse checkOrganPetition(AuthenticatedRequest<IdOrganPetitionRequest> ar);
	
	Set<OrganPetitionResponse> findByOrganPetitionState(OrganPetitionState organPetitionState);
}
