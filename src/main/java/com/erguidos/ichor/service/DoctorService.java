package com.erguidos.ichor.service;

import com.erguidos.ichor.dto.request.CreateUserRequestDTO;
import com.erguidos.ichor.dto.request.CreateUserRequestDTOV2;

public interface DoctorService {
	void createDoctor(CreateUserRequestDTO doctorRequestDTO);
	void createDoctorV2(CreateUserRequestDTOV2 doctorRequestDTO);
}
