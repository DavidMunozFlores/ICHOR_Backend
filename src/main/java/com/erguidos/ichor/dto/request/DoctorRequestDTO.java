package com.erguidos.ichor.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DoctorRequestDTO(
		String username,
		String password,
		@JsonProperty(value = "id_hospital")
		Long idHospital
		) {}
