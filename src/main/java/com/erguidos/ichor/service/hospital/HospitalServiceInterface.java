package com.erguidos.ichor.service.hospital;

import com.erguidos.ichor.entity.Hospital;

public interface HospitalServiceInterface {
    java.util.List<Hospital> getAllHospitals();

    Hospital getHospital(Long id);
}
