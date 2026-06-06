package com.erguidos.ichor.service.patient;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.erguidos.ichor.dto.request.PatientCreationRequest;
import com.erguidos.ichor.dto.request.PatientUpdateRequest;
import com.erguidos.ichor.entity.Hospital;
import com.erguidos.ichor.entity.Patient;
import com.erguidos.ichor.enums.ErrorCode;
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
    public Patient getPatient(Long id) {
        Optional<Patient> patientOp = this.patientRepository.findById(id);
        if (patientOp.isEmpty()) { throw ErrorCode.NOT_FOUND.throwIt(); }
        return patientOp.get();
    }

    @Override
    @Transactional
    public Patient createPatient(PatientCreationRequest data) {
        Optional<Patient> gotByInternalID = this.patientRepository.getByInternalID(data.internalID());
        if (gotByInternalID.isPresent()) { throw ErrorCode.ALREADY_EXISTS.throwIt(); }
        
        Optional<Patient> getByIndetification = this.patientRepository.getByIdentification(data.identification());
        if (getByIndetification.isPresent()) { throw ErrorCode.ALREADY_EXISTS.throwIt(); }
        
        Optional<BloodType> bloodType = BloodType.tryParse(data.bloodType());
        if (bloodType.isEmpty()) { throw ErrorCode.BLOOD_TYPE_NOT_EXISTS.throwIt(); }
        
        Optional<Hospital> hospital = this.hospitalRepository.findById(data.idHospital());
        if (hospital.isEmpty()) { throw ErrorCode.HOSPITAL_NOT_EXISTS.throwIt(); }
        
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
            
            return patient;
        } catch (BlankStringException bse) {
            throw ErrorCode.BLANK_STRING.throwIt();
        } catch (ImproperHeightException ihe) {
            throw ErrorCode.IMPROPER_HEIGHT.throwIt();
        } catch (ImproperWeightException iwe) {
            throw ErrorCode.IMPROPER_WEIGHT.throwIt();
        }
    }

    @Override
    @Transactional
    public Patient updatePatient(PatientUpdateRequest pur) {
        Optional<Patient> patientOp = this.patientRepository.findById(pur.id());
        if (patientOp.isEmpty()) { throw ErrorCode.USER_NOT_EXISTS.throwIt(); }
        try {
            Patient patient = patientOp.get();
            patient.updateHeightAndWeight(pur.height(), pur.weight());
            
            this.patientRepository.save(patient);
            return patient;
        } catch (ImproperHeightException ihe) {
            throw ErrorCode.IMPROPER_HEIGHT.throwIt();
        } catch (ImproperWeightException iwe) {
            throw ErrorCode.IMPROPER_WEIGHT.throwIt();
        }
    }
}
