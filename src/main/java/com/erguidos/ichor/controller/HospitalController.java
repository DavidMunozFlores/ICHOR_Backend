package com.erguidos.ichor.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erguidos.ichor.dto.response.HospitalResponse;
import com.erguidos.ichor.service.HospitalServiceInterface;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/v1/hospitals")
@CrossOrigin(origins = "http://localhost:4200")
public class HospitalController {
    private final HospitalServiceInterface hospitalService;
    
    HospitalController(HospitalServiceInterface hospitalService) {
        this.hospitalService = hospitalService;
    }
    
    @GetMapping
    public ResponseEntity<?> getAllHospitals() {
        List<HospitalResponse> hospitals = this.hospitalService.getAllHospitals();
        if (hospitals.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(hospitals);
    }
    
    
    
    
    @GetMapping("/populate")
    public ResponseEntity<Void> populate() {
        this.hospitalService.populate();
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
