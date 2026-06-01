package com.erguidos.ichor.controller.hospitals;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.erguidos.ichor.controller.AuthenticatedSemiController;
import com.erguidos.ichor.dto.mappers.HospitalMapper;
import com.erguidos.ichor.dto.types.SearchType;
import com.erguidos.ichor.entity.Hospital;
import com.erguidos.ichor.service.auth.AuthServiceInterface;
import com.erguidos.ichor.service.hospital.HospitalServiceInterface;
import com.erguidos.ichor.service.key.KeyServiceInterface;

@Component
public class HospitalSemiController extends AuthenticatedSemiController {
    private final HospitalServiceInterface hospitalService;
    
    HospitalSemiController(
        KeyServiceInterface keyService,
        AuthServiceInterface authService,
        HospitalServiceInterface hospitalService
    ) {
        super(keyService, authService);
        this.hospitalService = hospitalService;
    }
    
    public ResponseEntity<?> getAllHospitals() {
        return ResponseEntity.ok(
            HospitalMapper.toHospitalResponse(
                this.hospitalService.getAllHospitals()
            )
       );
    }
    
    public ResponseEntity<?> getCoordinator(Long id) {
        SearchType<Hospital> response = this.hospitalService.getHospital(id);
        return switch (response) {
            case SearchType.Found(Hospital hospital) ->
                ResponseEntity.ok(HospitalMapper.toHospitalResponse(hospital));
            case SearchType.Failed() ->
                ResponseEntity.notFound().build();
        };
    }
}
