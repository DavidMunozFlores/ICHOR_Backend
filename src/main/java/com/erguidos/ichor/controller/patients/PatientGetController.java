package com.erguidos.ichor.controller.patients;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.erguidos.ichor.dto.response.PatientResponse;
import com.erguidos.ichor.service.patient.PatientServiceInterface;
import com.erguidos.ichor.types.SearchType;

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
    public ResponseEntity<List<PatientResponse>> getAllPatients() {
        return ResponseEntity.ok(this.patientService.getAllPatients());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getPatient(@RequestParam Long id) {
        SearchType<PatientResponse> response = this.patientService.getPatient(id);
        return switch (response) {
            case SearchType.Found(PatientResponse patient) -> ResponseEntity.ok(patient);
            case SearchType.Failed() -> ResponseEntity.notFound().build();
        };
    }  
    
    @GetMapping("/identification/{identification}")
    public ResponseEntity<PatientResponse> findPatientByIdentification(@PathVariable String identification){
    	SearchType<PatientResponse> response = this.patientService.findPatitentByIdentification(identification);
    	
		return switch(response) {
			case SearchType.Found<PatientResponse>(PatientResponse pr) -> ResponseEntity.ok(pr);
			case SearchType.Failed<PatientResponse>() -> ResponseEntity.notFound().build();
		};
    }
}
