package com.erguidos.ichor.dto.request;

public record CreateUserRequestDTOV2(
		String username,
		String password,
		Long idHospital,
		LoginUserRequestDTO managerData
		) {}
