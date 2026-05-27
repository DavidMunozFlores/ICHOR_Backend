package com.erguidos.ichor.service.patient;

import java.util.List;

import com.erguidos.ichor.dto.response.PatientResponse;

public interface PatientServiceInterface {
    List<PatientResponse> getAllPatients();
}
