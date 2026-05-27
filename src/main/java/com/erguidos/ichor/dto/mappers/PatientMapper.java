package com.erguidos.ichor.dto.mappers;

import com.erguidos.ichor.dto.response.PatientResponse;
import com.erguidos.ichor.entity.Patient;

public final class PatientMapper {
    public static PatientResponse toPatientResponse(Patient patient) {
        return new PatientResponse(
            patient.getInternalID(),
            patient.getName(),
            patient.getIdentification(),
            patient.getBloodType().toString(),
            patient.getHeight(),
            patient.getWeight(),
            patient.getHospital().getId()
        );
    }
}
