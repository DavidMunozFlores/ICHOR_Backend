package com.erguidos.ichor.service.patient;

import java.util.List;

import org.springframework.stereotype.Service;

import com.erguidos.ichor.dto.mappers.PatientMapper;
import com.erguidos.ichor.dto.response.PatientResponse;
import com.erguidos.ichor.repository.PatientRepository;

@Service
public class PatientService implements PatientServiceInterface {
    private final PatientRepository patientRepository;
    
    PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }
    
    @Override
    public List<PatientResponse> getAllPatients() {
        return this.patientRepository.findAll()
                        .stream()
                        .map(PatientMapper::toPatientResponse)
                        .toList();
    }
}
