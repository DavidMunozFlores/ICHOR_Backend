package com.erguidos.ichor.dto.request;

public record CoordinatorCreateRequest(
    String username,
    String password,
    Long idHospital
) implements DataRequestInterface {}
