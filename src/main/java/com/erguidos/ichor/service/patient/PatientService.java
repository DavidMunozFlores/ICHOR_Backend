package com.erguidos.ichor.service.patient;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.erguidos.ichor.dto.request.PatientCreationRequest;
import com.erguidos.ichor.dto.request.PatientUpdateRequest;
import com.erguidos.ichor.entity.Hospital;
import com.erguidos.ichor.entity.Patient;
import com.erguidos.ichor.error.Errors;
import com.erguidos.ichor.enums.BloodType;
import com.erguidos.ichor.repository.HospitalRepository;
import com.erguidos.ichor.repository.PatientRepository;

import jakarta.transaction.Transactional;

@Service
public class PatientService implements PatientServiceInterface {
    private final PatientRepository patientRepository;
    private final HospitalRepository hospitalRepository;
    
    PatientService(PatientRepository patientRepository, HospitalRepository hospitalRepository) {
        this.patientRepository = patientRepository;
        this.hospitalRepository = hospitalRepository;
    }
    
    @Override
    public List<Patient> getAllPatients() {
        return this.patientRepository.findAll();
    }
    
    @Override
    public Patient getPatient(Long id) {
        Optional<Patient> patientOp = this.patientRepository.findById(id);
        if (patientOp.isEmpty()) { throw Errors.Patient.NOT_EXISTS.asException(); }
        return patientOp.get();
    }
    
	@Override
	public Patient findPatitentByIdentification(String identification) {
		Optional<Patient> patientOp = this.patientRepository.findByIdentification(identification);
		
		if(patientOp.isEmpty()) {
		    throw Errors.Patient.NOT_EXISTS.asException();
		}
		
		return patientOp.get();
	}

    @Override
    public Patient createPatient(PatientCreationRequest data) {
        Optional<Patient> gotByInternalID = this.patientRepository.getByInternalID(data.internalID());
        if (gotByInternalID.isPresent()) { throw Errors.Patient.ALREADY_EXISTS.asException(); }
        
        Optional<Patient> getByIndetification = this.patientRepository.getByIdentification(data.identification());
        if (getByIndetification.isPresent()) { throw Errors.Patient.ALREADY_EXISTS.asException(); }
        
        Optional<BloodType> bloodType = BloodType.tryParse(data.bloodType());
        if (bloodType.isEmpty()) { throw Errors.BloodType.NOT_EXISTS.asException(); }
        
        Optional<Hospital> hospital = this.hospitalRepository.findById(data.idHospital());
        if (hospital.isEmpty()) { throw Errors.Hospital.NOT_EXISTS.asException(); }
        
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
        
        return patient;
    }

    @Override
    @Transactional
    public Patient updatePatient(PatientUpdateRequest pur) {
        Optional<Patient> patientOp = this.patientRepository.findById(pur.id());
        if (patientOp.isEmpty()) { throw Errors.User.NOT_EXISTS.asException(); }
        
        Patient patient = patientOp.get();
        patient.updateHeightAndWeight(pur.height(), pur.weight());
        
        this.patientRepository.save(patient);
        return patient;
    }


}
