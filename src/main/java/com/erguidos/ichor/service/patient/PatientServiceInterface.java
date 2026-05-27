package com.erguidos.ichor.service.patient;

import java.util.List;

import com.erguidos.ichor.dto.response.PatientResponse;
import com.erguidos.ichor.types.SearchType;

public interface PatientServiceInterface {
    List<PatientResponse> getAllPatients();

    SearchType<PatientResponse> getPatient(Long id);
}
