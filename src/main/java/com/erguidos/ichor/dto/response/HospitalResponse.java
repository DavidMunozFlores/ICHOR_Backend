package com.erguidos.ichor.dto.response;

public record HospitalResponse(
    Long id,
    String name,
    String address,
    String longitude,
    String latitude
) {}
