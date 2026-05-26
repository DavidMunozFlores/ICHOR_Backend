package com.erguidos.ichor.service.auth;

import com.erguidos.ichor.dto.request.AuthCredentialsRequest;
import com.erguidos.ichor.dto.response.IsUserAuthorizedResponse;
import com.erguidos.ichor.service.Role;

public interface AuthServiceInterface {
	IsUserAuthorizedResponse isAuthorized(AuthCredentialsRequest userRequestDTO);

    boolean authenticate(AuthCredentialsRequest acr, Role roleAllowed);
}
