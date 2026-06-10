package com.erguidos.ichor.dto.mappers;

import java.util.List;

import com.erguidos.ichor.dto.response.PatientResponse;
import com.erguidos.ichor.entity.Patient;

public final class PatientMapper {
    private PatientMapper() {
        throw new UnsupportedOperationException("Don't instantiate PatientMapper");
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
            OrganPetitionMapper.toOrganPetitionResponse(patient.getOrganPetitions())
        );
    }
    
    public static List<PatientResponse> toPatientResponse(List<Patient> patients) {
        return patients.stream()
                .map(PatientMapper::toPatientResponse)
                .toList();
    }
}
