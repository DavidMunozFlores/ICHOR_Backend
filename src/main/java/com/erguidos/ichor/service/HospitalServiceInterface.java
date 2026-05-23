package com.erguidos.ichor.service;

import com.erguidos.ichor.dto.response.HospitalResponse;

public interface HospitalServiceInterface {
    java.util.List<HospitalResponse> getAllHospitals();
    
    
    // TODO: remove
    void populate();
}
