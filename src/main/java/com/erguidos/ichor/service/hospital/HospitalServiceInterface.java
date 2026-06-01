package com.erguidos.ichor.service.hospital;

import com.erguidos.ichor.dto.types.SearchType;
import com.erguidos.ichor.entity.Hospital;

public interface HospitalServiceInterface {
    java.util.List<Hospital> getAllHospitals();

    SearchType<Hospital> getHospital(Long id);
}
