package com.erguidos.ichor.controller.patients;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erguidos.ichor.dto.mappers.ListMapper;
import com.erguidos.ichor.dto.mappers.PatientMapper;
import com.erguidos.ichor.dto.response.ListWrapper;
import com.erguidos.ichor.dto.response.PatientResponse;
import com.erguidos.ichor.entity.Patient;
import com.erguidos.ichor.service.patient.PatientServiceInterface;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/v1/patients")
@CrossOrigin(origins = {"${app.cors.allowed}"})
public class PatientGetController {
    private final PatientServiceInterface patientService;
    
    PatientGetController(PatientServiceInterface patientService) {
        this.patientService = patientService;
    }
    
    @GetMapping
    public ResponseEntity<ListWrapper<PatientResponse>> getAllPatients() {
        return ResponseEntity.ok(
            ListMapper.toListWrapper(
                this.patientService.getAllPatients(),
                PatientMapper::toPatientResponse
            )
        );
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getPatient(@PathVariable Long id) {
        Patient patient = this.patientService.getPatient(id);
        return ResponseEntity.ok(PatientMapper.toPatientResponse(patient));
    }  
}
