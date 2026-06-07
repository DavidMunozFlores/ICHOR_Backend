package com.erguidos.ichor.service.doctor;

import java.util.List;

import com.erguidos.ichor.dto.request.CreateWorkerRequest;
import com.erguidos.ichor.dto.response.DoctorResponse;
import com.erguidos.ichor.dto.response.WorkerCreatedResponse;

public interface DoctorServiceInterface {
    WorkerCreatedResponse createDoctor(CreateWorkerRequest createWorkerRequest);
	List<DoctorResponse> getAllDoctors();
	DoctorResponse getDoctorById(Long id);
	List<DoctorResponse> getDoctorsByNameContainingIgnoreCase(String name);
}
