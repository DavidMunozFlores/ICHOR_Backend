package com.erguidos.ichor.dto.request;

public record CreateWorkerRequest(String username, String password, Long idHospital)
	   implements DataRequestInterface {}
