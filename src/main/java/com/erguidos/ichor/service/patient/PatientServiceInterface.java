package com.erguidos.ichor.service.patient;

import java.util.List;

import com.erguidos.ichor.dto.request.PatientCreationRequest;
import com.erguidos.ichor.dto.request.PatientUpdateRequest;
import com.erguidos.ichor.dto.response.PatientResponse;
import com.erguidos.ichor.types.PatientCreationType;
import com.erguidos.ichor.types.SearchType;

public interface PatientServiceInterface {
    List<PatientResponse> getAllPatients();

    SearchType<PatientResponse> getPatient(Long id);

    PatientCreationType createPatient(PatientCreationRequest data);

    PatientUpdateType updatePatient(PatientUpdateRequest pur);
    
    SearchType<PatientResponse> findPatitentByIdentification(String identification);
}
