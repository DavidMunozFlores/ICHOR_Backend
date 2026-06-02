package com.erguidos.ichor.service.patient;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.erguidos.ichor.dto.request.PatientCreationRequest;
import com.erguidos.ichor.dto.request.PatientUpdateRequest;
import com.erguidos.ichor.dto.types.PatientCreationType;
import com.erguidos.ichor.dto.types.PatientUpdateType;
import com.erguidos.ichor.dto.types.SearchType;
import com.erguidos.ichor.entity.Hospital;
import com.erguidos.ichor.entity.Patient;
import com.erguidos.ichor.enums.BadRequest;
import com.erguidos.ichor.enums.BloodType;
import com.erguidos.ichor.exceptions.BlankStringException;
import com.erguidos.ichor.exceptions.ImproperHeightException;
import com.erguidos.ichor.exceptions.ImproperWeightException;
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
    public SearchType<Patient> getPatient(Long id) {
        Optional<Patient> patientOp = this.patientRepository.findById(id);
        if (patientOp.isEmpty()) { return new SearchType.Failed<Patient>(); }
        return new SearchType.Found<Patient>(patientOp.get());
    }

    @Override
    public PatientCreationType createPatient(PatientCreationRequest data) {
        Optional<Patient> gotByInternalID = this.patientRepository.getByInternalID(data.internalID());
        if (gotByInternalID.isPresent()) { return new PatientCreationType.Exists(); }
        
        Optional<Patient> getByIndetification = this.patientRepository.getByIdentification(data.identification());
        if (getByIndetification.isPresent()) { return new PatientCreationType.Exists(); }
        
        Optional<BloodType> bloodType = BloodType.tryParse(data.bloodType());
        if (bloodType.isEmpty()) { return new PatientCreationType.Failure(BadRequest.BLOOD_TYPE_NOT_EXISTS); }
        
        Optional<Hospital> hospital = this.hospitalRepository.findById(data.idHospital());
        if (hospital.isEmpty()) { return new PatientCreationType.Failure(BadRequest.HOSPITAL_NOT_EXISTS); }
        
        try {
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
            
            return new PatientCreationType.Created(patient);
        } catch (BlankStringException bse) {
            return new PatientCreationType.Failure(BadRequest.BLANK_STRING);
        } catch (ImproperHeightException ige) {
            return new PatientCreationType.Failure(BadRequest.IMPROPER_HEIGHT);
        } catch (ImproperWeightException iwe) {
            return new PatientCreationType.Failure(BadRequest.IMPROPER_WEIGHT);
        }
    }

    @Override
    @Transactional
    public PatientUpdateType updatePatient(PatientUpdateRequest pur) {
        Optional<Patient> patientOp = this.patientRepository.findById(pur.id());
        if (patientOp.isEmpty()) { return new PatientUpdateType.Failure(BadRequest.USER_NOT_EXISTS); }
        try {
            Patient patient = patientOp.get();
            patient.updateHeightAndWeight(pur.height(), pur.weight());
            
            this.patientRepository.save(patient);
            return new PatientUpdateType.Success(patient);
        } catch (ImproperHeightException ihe) {
            return new PatientUpdateType.Failure(BadRequest.IMPROPER_HEIGHT);
        } catch (ImproperWeightException iwe) {
            return new PatientUpdateType.Failure(BadRequest.IMPROPER_WEIGHT);
        }
    }
}
