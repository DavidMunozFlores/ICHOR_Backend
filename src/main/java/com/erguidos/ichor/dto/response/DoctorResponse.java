package com.erguidos.ichor.dto.response;

public record DoctorResponse(
    Long id,
    String username,
    Long hospitalId
) {}