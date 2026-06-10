package com.erguidos.ichor.dto.request;

public record AuthenticatedRequest<D extends DataRequestInterface>(
	AuthCredentialsRequest authCredentials,
	D data
) {}
	