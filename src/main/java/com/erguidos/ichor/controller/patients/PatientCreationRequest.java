package com.erguidos.ichor.controller.patients;

import com.erguidos.ichor.dto.request.DataRequestInterface;

public record PatientCreationRequest(
    String internalID,
    String name,
    String identification,
    String bloodType,
    Double height,
    Double weight,
    Long   idHospital
) implements DataRequestInterface {}
