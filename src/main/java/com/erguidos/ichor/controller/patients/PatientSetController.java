package com.erguidos.ichor.controller.patients;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erguidos.ichor.dto.request.DecryptRequest;


@RestController
@RequestMapping("/api/v1/patients")
@CrossOrigin(origins = {"${app.cors.allowed}"})
public class PatientSetController {
    private final PatientSemiController semi;
    
    PatientSetController(PatientSemiController semi) {
        this.semi = semi;
    }
    
    @PostMapping("/create")
    public ResponseEntity<?> createPatient(@RequestBody DecryptRequest dr) {
        return this.semi.createPatient(dr);
    }
    
    @PatchMapping
    public ResponseEntity<?> updatePatient(@RequestBody DecryptRequest dr) {
        return this.semi.updatePatient(dr);
    }
}
