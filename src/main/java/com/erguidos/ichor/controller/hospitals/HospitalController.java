package com.erguidos.ichor.controller.hospitals;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erguidos.ichor.dto.mappers.HospitalMapper;
import com.erguidos.ichor.dto.mappers.ListMapper;
import com.erguidos.ichor.dto.response.HospitalResponse;
import com.erguidos.ichor.dto.response.ListWrapper;
import com.erguidos.ichor.dto.types.SearchType;
import com.erguidos.ichor.entity.Hospital;
import com.erguidos.ichor.service.hospital.HospitalServiceInterface;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1/hospitals")
@CrossOrigin(origins = {"${app.cors.allowed}"})
public class HospitalController {
    private final HospitalServiceInterface hospitalService;
    
    HospitalController(HospitalServiceInterface hospitalService) {
        this.hospitalService = hospitalService;
    }
    
    @GetMapping
    public ResponseEntity<ListWrapper<HospitalResponse>> getAllHospitals() {
        return ResponseEntity.ok(
            ListMapper.toListWrapper(
                this.hospitalService.getAllHospitals(),
                HospitalMapper::toHospitalResponse
            )
        );
    }
    
    public ResponseEntity<HospitalResponse> getHospital(Long id) {
        SearchType<Hospital> response = this.hospitalService.getHospital(id);
        return switch (response) {
            case SearchType.Found(Hospital hospital) ->
                ResponseEntity.ok(HospitalMapper.toHospitalResponse(hospital));
            case SearchType.Failed() ->
                ResponseEntity.notFound().build();
        };
    }
}
