package com.erguidos.ichor.dto.response;

import java.util.List;

public record PatientResponse(
	Long idPatient,
    String internalID,
    String name,
    String identification,
    String bloodType,
    Double height,
    Double weight,
    Long   idHospital,
    List<OrganPetitionResponse> organPetitions
) {}
