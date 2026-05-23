package com.erguidos.ichor.service;

import com.erguidos.ichor.dto.response.HospitalResponse;
import com.erguidos.ichor.entity.Hospital;
import com.erguidos.ichor.repository.HospitalRepository;
import com.erguidos.ichor.utils.IchorUtils;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class HospitalService implements HospitalServiceInterface {
    private final HospitalRepository hospitalRepository;
    
    HospitalService(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }
    
    @Override
    public List<HospitalResponse> getAllHospitals() {
        return this.hospitalRepository.findAll()
                .stream()
                .map(HospitalResponse::of)
                .toList();
    }

    
    @Override
    public void populate() {
        for (int ii = 0; ii < 10; ii++) {
            Hospital h = Hospital.builder()
                    .setAddress(IchorUtils.randomString())
                    .setName(IchorUtils.randomString())
                    .setLongitude(IchorUtils.randomLongitude())
                    .setLatitude(IchorUtils.randomLatitude())
                    .build();
            
            this.hospitalRepository.save(h);
        }
    }
}
