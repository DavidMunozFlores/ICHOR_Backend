package com.erguidos.ichor.dto.request;

public record CreateUserRequest(String username, String password, Long idHospital)
	   implements DataRequestInterface {}
