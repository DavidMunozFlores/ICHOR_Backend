package com.erguidos.ichor.service.patient;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.erguidos.ichor.dto.mappers.PatientMapper;
import com.erguidos.ichor.dto.response.PatientResponse;
import com.erguidos.ichor.entity.Patient;
import com.erguidos.ichor.repository.PatientRepository;
import com.erguidos.ichor.types.SearchType;

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
    
    @Override
    public SearchType<PatientResponse> getPatient(Long id) {
        Optional<Patient> patientOp = this.patientRepository.findById(id);
        if (patientOp.isEmpty()) { return new SearchType.Failed<PatientResponse>(); }
        return new SearchType.Found<PatientResponse>(
                PatientMapper.toPatientResponse(patientOp.get())
        );
    }
}
