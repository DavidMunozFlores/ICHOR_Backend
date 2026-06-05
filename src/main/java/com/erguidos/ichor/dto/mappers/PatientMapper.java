package com.erguidos.ichor.dto.mappers;

import com.erguidos.ichor.dto.response.PatientResponse;
import com.erguidos.ichor.entity.Patient;

public final class PatientMapper {
    private PatientMapper() throws IllegalAccessException {
        throw new IllegalAccessException("Don't instantiate PatientMapper");
    }
    
    public static PatientResponse toPatientResponse(Patient patient) {
        return new PatientResponse(
        	patient.getId(),
            patient.getInternalID(),
            patient.getName(),
            patient.getIdentification(),
            patient.getBloodType().toString(),
            patient.getHeight(),
            patient.getWeight(),
            patient.getHospital().getId(),
            patient.getOrganPetitions().stream().map(OrganPetitionMapper::toOrganPetitionResponse).toList()
        );
    }
}
