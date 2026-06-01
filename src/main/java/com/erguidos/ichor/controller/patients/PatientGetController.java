package com.erguidos.ichor.controller.patients;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/v1/patients")
@CrossOrigin(origins = {"${app.cors.allowed}"})
public class PatientGetController {
    private final PatientSemiController semi;
    
    PatientGetController(PatientSemiController semi) {
        this.semi = semi;
    }
    
    @GetMapping
    public ResponseEntity<?> getAllPatients() {
        return this.semi.getAllPatients();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getPatient(@PathVariable Long id) {
        return this.semi.getPatient(id);
    }  
}
