package com.erguidos.ichor.dto.mappers;

import com.erguidos.ichor.dto.response.HospitalResponse;
import com.erguidos.ichor.entity.Hospital;

public final class HospitalMapper {
    public static HospitalResponse toHospitalResponse(Hospital hospital) {
        return new HospitalResponse(
            hospital.getId(),
            hospital.getName(),
            hospital.getAddress(),
            hospital.getLongitude().toPlainString(),
            hospital.getLatitude().toPlainString()
        );
    }
}
