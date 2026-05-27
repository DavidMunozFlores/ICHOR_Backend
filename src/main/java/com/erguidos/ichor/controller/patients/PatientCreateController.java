package com.erguidos.ichor.controller.patients;

import java.security.GeneralSecurityException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erguidos.ichor.dto.request.AuthenticatedRequest;
import com.erguidos.ichor.dto.request.DecryptRequest;
import com.erguidos.ichor.dto.response.PatientResponse;
import com.erguidos.ichor.enums.Role;
import com.erguidos.ichor.service.auth.AuthServiceInterface;
import com.erguidos.ichor.service.key.KeyServiceInterface;
import com.erguidos.ichor.service.patient.PatientServiceInterface;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/patients")
@CrossOrigin(origins = {"${app.cors.allowed}"})
public class PatientCreateController {
    private final PatientServiceInterface patientService;
    private final KeyServiceInterface keyService;
    private final AuthServiceInterface authService;
    
    PatientCreateController(
        PatientServiceInterface patientService,
        KeyServiceInterface keyService,
        AuthServiceInterface authService
    ) {
        this.patientService = patientService;
        this.keyService = keyService;
        this.authService = authService;
    }
    
    @PostMapping("/create")
    public ResponseEntity<?> createPacient(@RequestBody DecryptRequest dr)
            throws JsonProcessingException, GeneralSecurityException {
        AuthenticatedRequest<PatientCreationRequest> ar = this.keyService.decryptToAuthenticatedRequest(dr, PatientCreationRequest.class);
        this.authService.authenticate(ar.authCredentials(), Role.DOCTOR);
        PatientCreationType pct = this.patientService.createPatient(ar.data());
        
        return switch (pct) {
            case PatientCreationType.Created(PatientResponse pr) ->
                    ResponseEntity.status(HttpStatus.CREATED).body(pr);
            case PatientCreationType.Exists() -> 
                    ResponseEntity.status(HttpStatus.CONFLICT).build();
            case PatientCreationType.NoHospital() ->
                    ResponseEntity.badRequest().body("HOSPITAL_NOT_EXISTS");
            case PatientCreationType.BadBlood() ->
                    ResponseEntity.badRequest().body("BLOOD_TYPE_NOT_EXISTS");
        };
    }
}
