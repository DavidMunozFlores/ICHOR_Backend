package com.erguidos.ichor.dto.response;

public record PatientResponse(
    String internalID,
    String name,
    String identification,
    String bloodType,
    Double height,
    Double weight,
    Long   idHospital
) {}
