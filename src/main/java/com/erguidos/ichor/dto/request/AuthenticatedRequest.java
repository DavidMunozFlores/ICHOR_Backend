package com.erguidos.ichor.dto.request;

public record AuthenticatedRequest(
		AuthCredentialsRequest authCredentials,
		CreateWorkerRequest data) {}
	