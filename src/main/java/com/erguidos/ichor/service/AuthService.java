package com.erguidos.ichor.service;

import com.erguidos.ichor.dto.request.LoginUserRequestDTO;
import com.erguidos.ichor.dto.response.IsUserAuthorizedDTO;

public interface AuthService {
	IsUserAuthorizedDTO isAuthorized(LoginUserRequestDTO userRequestDTO);
}
