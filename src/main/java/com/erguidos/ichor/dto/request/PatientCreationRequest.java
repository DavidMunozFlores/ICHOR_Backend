package com.erguidos.ichor.dto.request;

public record PatientCreationRequest(
    String internalID,
    String name,
    String identification,
    String bloodType,
    Double height,
    Double weight,
    Long   idHospital
) implements DataRequestInterface {}
