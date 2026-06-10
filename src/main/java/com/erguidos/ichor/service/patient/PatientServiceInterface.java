package com.erguidos.ichor.service.patient;

import java.util.List;

import com.erguidos.ichor.dto.request.PatientCreationRequest;
import com.erguidos.ichor.dto.request.PatientUpdateRequest;
import com.erguidos.ichor.entity.Patient;

public interface PatientServiceInterface {
    List<Patient> getAllPatients();

    Patient getPatient(Long id);

    Patient createPatient(PatientCreationRequest data);

    Patient findPatitentByIdentification(String identification);

    Patient updatePatient(PatientUpdateRequest pur);

}
