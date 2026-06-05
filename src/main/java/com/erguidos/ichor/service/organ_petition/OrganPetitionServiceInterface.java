package com.erguidos.ichor.service.organ_petition;

import com.erguidos.ichor.dto.request.UpdateOrganPetitionRequest;

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
	
	OPDeleted deleteOrganPetition(AuthenticatedRequest<UpdateOrganPetitionRequest> ar);
	
	OrganPetitionResponse acceptOrganPetition(AuthenticatedRequest<UpdateOrganPetitionRequest> ar);
	
	OrganPetitionResponse cancelOrganPetition(AuthenticatedRequest<UpdateOrganPetitionRequest> ar);
	
	OrganPetitionResponse checkOrganPetition(AuthenticatedRequest<UpdateOrganPetitionRequest> ar);
	
	Set<OrganPetitionResponse> findByOrganPetitionState(OrganPetitionState organPetitionState);
}
