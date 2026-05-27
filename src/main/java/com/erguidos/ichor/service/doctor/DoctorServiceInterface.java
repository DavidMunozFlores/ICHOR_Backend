package com.erguidos.ichor.service.doctor;

import java.security.GeneralSecurityException;
import java.util.List;

import com.erguidos.ichor.dto.request.DecryptRequest;
import com.erguidos.ichor.dto.response.DoctorResponse;
import com.erguidos.ichor.dto.response.WorkerCreatedResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface DoctorServiceInterface {
	WorkerCreatedResponse createDoctor(DecryptRequest decryptedDTO) throws JsonProcessingException, GeneralSecurityException;
	List<DoctorResponse> getAllDoctors();
	DoctorResponse getDoctorById(Long id);
}
