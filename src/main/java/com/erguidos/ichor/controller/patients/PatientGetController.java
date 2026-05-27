package com.erguidos.ichor.controller.patients;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erguidos.ichor.service.patient.PatientServiceInterface;

@RestController
@RequestMapping("/api/v1/patients")
@CrossOrigin(origins = {"${app.cors.allowed}"})
public class PatientGetController {
    private final PatientServiceInterface patientService;
    
    PatientGetController(PatientServiceInterface patientService) {
        this.patientService = patientService;
    }
    
    
}
