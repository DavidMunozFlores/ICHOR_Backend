package com.erguidos.ichor.dto.request;

public record PatientUpdateRequest(
    Long id,
    Double height,
    Double weight
) implements DataRequestInterface {}
