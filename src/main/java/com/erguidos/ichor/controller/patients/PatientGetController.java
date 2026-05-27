package com.erguidos.ichor.controller.patients;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erguidos.ichor.dto.response.PatientResponse;
import com.erguidos.ichor.service.patient.PatientServiceInterface;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/v1/patients")
@CrossOrigin(origins = {"${app.cors.allowed}"})
public class PatientGetController {
    private final PatientServiceInterface patientService;
    
    PatientGetController(PatientServiceInterface patientService) {
        this.patientService = patientService;
    }
    
    @GetMapping
    public ResponseEntity<List<PatientResponse>> getAllPatients() {
        return ResponseEntity.ok(this.patientService.getAllPatients());
    }
    
}
