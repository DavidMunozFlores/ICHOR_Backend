package com.erguidos.ichor.dto.response;

import com.erguidos.ichor.entity.Hospital;

public record HospitalResponse(
    Long id,
    String name,
    String address,
    String longitude,
    String latitude
) {
    public static HospitalResponse of(Hospital hospital) {
        return new HospitalResponse(
            hospital.getId(),
            hospital.getName(),
            hospital.getAddress(),
            hospital.getLongitude().toPlainString(),
            hospital.getLatitude().toPlainString()
        );
    }
}
