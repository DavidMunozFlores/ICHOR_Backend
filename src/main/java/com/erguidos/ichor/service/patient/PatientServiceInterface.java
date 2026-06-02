package com.erguidos.ichor.service.patient;

import java.util.List;

import com.erguidos.ichor.dto.request.PatientCreationRequest;
import com.erguidos.ichor.dto.request.PatientUpdateRequest;
import com.erguidos.ichor.dto.types.PatientCreationType;
import com.erguidos.ichor.dto.types.PatientUpdateType;
import com.erguidos.ichor.dto.types.SearchType;
import com.erguidos.ichor.entity.Patient;

public interface PatientServiceInterface {
    List<Patient> getAllPatients();

    SearchType<Patient> getPatient(Long id);

    PatientCreationType createPatient(PatientCreationRequest data);

    PatientUpdateType updatePatient(PatientUpdateRequest pur);
}
