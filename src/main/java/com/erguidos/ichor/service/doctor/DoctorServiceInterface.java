package com.erguidos.ichor.service.doctor;

import com.erguidos.ichor.dto.request.CreateUserRequestDTOV2;
import com.erguidos.ichor.dto.request.DecryptRequest;

public interface DoctorServiceInterface {
	void createDoctor(DecryptRequest decryptedDTO);
	void createDoctorV2(CreateUserRequestDTOV2 doctorRequestDTO);
}
