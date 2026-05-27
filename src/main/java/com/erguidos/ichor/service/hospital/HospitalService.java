package com.erguidos.ichor.service.hospital;

import com.erguidos.ichor.dto.mappers.HospitalMapper;
import com.erguidos.ichor.dto.response.HospitalResponse;
import com.erguidos.ichor.repository.HospitalRepository;

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
                .map(HospitalMapper::toHospitalResponse)
                .toList();
    }
}
