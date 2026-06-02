package com.erguidos.ichor.service.organ;

import java.util.List;

import com.erguidos.ichor.dto.request.AuthenticatedRequest;
import com.erguidos.ichor.dto.request.RegisterOrganRequest;
import com.erguidos.ichor.dto.response.OrganBloodTypeInfoResponse;
import com.erguidos.ichor.dto.response.OrganTypeInfoResponse;
import com.erguidos.ichor.dto.response.RegisterOrganResponse;

public interface OrganServiceInterface {
	List<OrganTypeInfoResponse> getOrganTypeInfo();
	List<OrganBloodTypeInfoResponse> getOrganBloodTypeInfo();
	RegisterOrganResponse registerOrgan(AuthenticatedRequest<RegisterOrganRequest> ar);
}
