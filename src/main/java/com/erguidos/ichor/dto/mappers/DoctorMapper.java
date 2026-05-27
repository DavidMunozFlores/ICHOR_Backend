package com.erguidos.ichor.dto.mappers;

import com.erguidos.ichor.dto.response.DoctorResponse;
import com.erguidos.ichor.entity.Doctor;

public class DoctorMapper {

	private DoctorMapper() {}
	
	public static DoctorResponse toDoctorResponse(Doctor d) {
		return new DoctorResponse(d.getId(),d.getUsername(),d.getHospital().getId());
	}
}
