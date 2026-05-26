package com.erguidos.ichor.service.doctor;

import java.security.GeneralSecurityException;

import com.erguidos.ichor.dto.request.DecryptRequest;
import com.erguidos.ichor.dto.response.WorkerCreatedResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface DoctorServiceInterface {
	WorkerCreatedResponse createDoctor(DecryptRequest decryptedDTO) throws JsonProcessingException, GeneralSecurityException;
}
