package com.erguidos.ichor.service.patient;

import org.springframework.stereotype.Service;

import com.erguidos.ichor.repository.PatientRepository;

@Service
public class PatientService implements PatientServiceInterface {
    private final PatientRepository patientRepository;
    
    PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }
}
