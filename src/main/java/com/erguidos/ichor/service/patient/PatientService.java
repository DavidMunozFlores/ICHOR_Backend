package com.erguidos.ichor.service.patient;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.erguidos.ichor.controller.patients.PatientCreationRequest;
import com.erguidos.ichor.controller.patients.PatientCreationType;
import com.erguidos.ichor.dto.mappers.PatientMapper;
import com.erguidos.ichor.dto.response.PatientResponse;
import com.erguidos.ichor.entity.Hospital;
import com.erguidos.ichor.entity.Patient;
import com.erguidos.ichor.enums.BloodType;
import com.erguidos.ichor.repository.HospitalRepository;
import com.erguidos.ichor.repository.PatientRepository;
import com.erguidos.ichor.types.SearchType;

@Service
public class PatientService implements PatientServiceInterface {
    private final PatientRepository patientRepository;
    private final HospitalRepository hospitalRepository;
    
    PatientService(PatientRepository patientRepository, HospitalRepository hospitalRepository) {
        this.patientRepository = patientRepository;
        this.hospitalRepository = hospitalRepository;
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

    @Override
    public PatientCreationType createPatient(PatientCreationRequest data) {
        Optional<Patient> gotByInternalID = this.patientRepository.getByInternalID(data.internalID());
        if (gotByInternalID.isPresent()) { return new PatientCreationType.Exists(); }
        
        Optional<Patient> getByIndetification = this.patientRepository.getByIdentification(data.identification());
        if (getByIndetification.isPresent()) { return new PatientCreationType.Exists(); }
        
        Optional<BloodType> bloodType = BloodType.tryParse(data.bloodType());
        if (bloodType.isEmpty()) { return new PatientCreationType.BadBlood(); }
        
        Optional<Hospital> hospital = this.hospitalRepository.findById(data.idHospital());
        if (hospital.isEmpty()) { return new PatientCreationType.NoHospital(); }
        
        Patient patient = Patient.builder()
                .setInternalID(data.internalID())
                .setName(data.name())
                .setIdentification(data.identification())
                .setBloodType(bloodType.get())
                .setHeight(data.height())
                .setWeight(data.weight())
                .setHospital(hospital.get())
                .build();
  
        this.patientRepository.save(patient);
        
        return new PatientCreationType.Created(PatientMapper.toPatientResponse(patient));
    }
}
