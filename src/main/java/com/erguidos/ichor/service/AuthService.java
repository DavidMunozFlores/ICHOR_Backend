package com.erguidos.ichor.service;

import com.erguidos.ichor.dto.request.UserRequestDTO;
import com.erguidos.ichor.dto.response.IsAuthorizedDTO;

public interface AuthService {
	IsAuthorizedDTO loginUser(UserRequestDTO userRequestDTO);
}
