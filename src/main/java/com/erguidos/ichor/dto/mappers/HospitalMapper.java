package com.erguidos.ichor.dto.mappers;

import java.util.List;

import com.erguidos.ichor.dto.response.HospitalResponse;
import com.erguidos.ichor.entity.Hospital;

public final class HospitalMapper {
    private HospitalMapper() {
        throw new UnsupportedOperationException("Don't instantiate HospitalMapper");
    }
    
    public static HospitalResponse toHospitalResponse(Hospital hospital) {
        return new HospitalResponse(
            hospital.getId(),
            hospital.getName(),
            hospital.getAddress(),
            hospital.getLongitude().toPlainString(),
            hospital.getLatitude().toPlainString()
        );
    }
    
    public static List<HospitalResponse> toHospitalResponse(List<Hospital> hospitals) {
        return hospitals.stream().map(HospitalMapper::toHospitalResponse).toList();
    }
}
