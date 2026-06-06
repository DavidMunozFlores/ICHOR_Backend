package com.erguidos.ichor.service.hospital;

import com.erguidos.ichor.entity.Hospital;
import com.erguidos.ichor.enums.ErrorCode;
import com.erguidos.ichor.repository.HospitalRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class HospitalService implements HospitalServiceInterface {
    private final HospitalRepository hospitalRepository;
    
    HospitalService(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }
    
    @Override
    public List<Hospital> getAllHospitals() {
        return this.hospitalRepository.findAll();
    }

    @Override
    public Hospital getHospital(Long id) {
        Optional<Hospital> hospitalOp = this.hospitalRepository.findById(id);
        if (hospitalOp.isEmpty()) { throw ErrorCode.NOT_FOUND.throwIt(); }
        return hospitalOp.get();
    }
}
