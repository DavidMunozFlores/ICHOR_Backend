package com.erguidos.ichor.controller.hospitals;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1/hospitals")
@CrossOrigin(origins = {"${app.cors.allowed}"})
public class HospitalController {
    private final HospitalSemiController semi;
    
    HospitalController(HospitalSemiController semi) {
        this.semi = semi;
    }
    
    @GetMapping
    public ResponseEntity<?> getAllHospitals() {
        return this.semi.getAllHospitals();
    }
}
