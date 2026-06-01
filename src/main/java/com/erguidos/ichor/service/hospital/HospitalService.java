package com.erguidos.ichor.service.hospital;

import com.erguidos.ichor.dto.types.SearchType;
import com.erguidos.ichor.entity.Hospital;
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
    public SearchType<Hospital> getHospital(Long id) {
        Optional<Hospital> hospitalOp = this.hospitalRepository.findById(id);
        if (hospitalOp.isEmpty()) { return new SearchType.Failed<Hospital>(); }
        return new SearchType.Found<Hospital>(hospitalOp.get());
    }
}
