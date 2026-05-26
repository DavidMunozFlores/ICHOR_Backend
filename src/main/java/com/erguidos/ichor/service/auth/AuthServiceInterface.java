package com.erguidos.ichor.service.auth;

import com.erguidos.ichor.dto.request.AuthCredentialsRequest;
import com.erguidos.ichor.dto.response.IsUserAuthorizedResponse;

public interface AuthServiceInterface {
	IsUserAuthorizedResponse isAuthorized(AuthCredentialsRequest userRequestDTO);
}
