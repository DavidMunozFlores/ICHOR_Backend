package com.erguidos.ichor.service.auth;

import com.erguidos.ichor.dto.request.AuthCredentialsRequest;
import com.erguidos.ichor.dto.response.IsUserAuthorizedDTO;

public interface AuthServiceInterface {
	IsUserAuthorizedDTO isAuthorized(AuthCredentialsRequest userRequestDTO);
}
