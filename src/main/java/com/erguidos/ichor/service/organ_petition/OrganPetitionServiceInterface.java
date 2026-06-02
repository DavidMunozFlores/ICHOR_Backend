package com.erguidos.ichor.service.organ_petition;

import com.erguidos.ichor.dto.request.StateUpdateOrganPetitionRequest;
import com.erguidos.ichor.dto.request.AuthenticatedRequest;
import com.erguidos.ichor.dto.request.OrganPetitionRequest;
import com.erguidos.ichor.dto.response.OrganPetitionResponse;

public interface OrganPetitionServiceInterface {
	OrganPetitionResponse createOrganPetition(AuthenticatedRequest<OrganPetitionRequest> ar);
	OrganPetitionResponse acceptOrganPetition(AuthenticatedRequest<StateUpdateOrganPetitionRequest> ar);
	OrganPetitionResponse cancellOrganPetition(AuthenticatedRequest<StateUpdateOrganPetitionRequest> ar);
}
