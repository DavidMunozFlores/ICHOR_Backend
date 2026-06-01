package com.erguidos.ichor.controller.patients;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.erguidos.ichor.controller.AuthenticatedSemiController;
import com.erguidos.ichor.dto.mappers.PatientMapper;
import com.erguidos.ichor.dto.request.DecryptRequest;
import com.erguidos.ichor.dto.request.PatientCreationRequest;
import com.erguidos.ichor.dto.request.PatientUpdateRequest;
import com.erguidos.ichor.dto.types.PatientCreationType;
import com.erguidos.ichor.dto.types.SearchType;
import com.erguidos.ichor.entity.Patient;
import com.erguidos.ichor.enums.BadRequest;
import com.erguidos.ichor.enums.Role;
import com.erguidos.ichor.service.auth.AuthServiceInterface;
import com.erguidos.ichor.service.key.KeyServiceInterface;
import com.erguidos.ichor.service.patient.PatientServiceInterface;
import com.erguidos.ichor.service.patient.PatientUpdateType;


@Component
public class PatientSemiController extends AuthenticatedSemiController {
    private final PatientServiceInterface patientService;
    
    PatientSemiController(
        KeyServiceInterface keyService,
        AuthServiceInterface authService,
        PatientServiceInterface patientService
    ) {
        super(keyService, authService);
        this.patientService = patientService;
    }

    public ResponseEntity<?> createPatient(DecryptRequest dr) {
        return this.process(
            dr,
            PatientCreationRequest.class,
            Role.DOCTOR,
            this::createWithData
        );
    }
    
    private ResponseEntity<?> createWithData(PatientCreationRequest data) {
        PatientCreationType pct = this.patientService.createPatient(data);
        
        return switch (pct) {
            case PatientCreationType.Created(Patient patient) ->
                ResponseEntity.status(HttpStatus.CREATED).body(PatientMapper.toPatientResponse(patient));
            case PatientCreationType.Exists() ->
                ResponseEntity.status(HttpStatus.CONFLICT).build();
            case PatientCreationType.Failure(BadRequest br) ->
                ResponseEntity.badRequest().body(br);
        };
    }
    
    public ResponseEntity<?> updatePatient(DecryptRequest dr) {
        return this.process(
            dr,
            PatientUpdateRequest.class,
            Role.DOCTOR,
            this::updateWithData
        );
    }
    
    private ResponseEntity<?> updateWithData(PatientUpdateRequest data) {
        PatientUpdateType put = this.patientService.updatePatient(data);
        
        return switch (put) {
            case PatientUpdateType.NotExists() ->
                ResponseEntity.badRequest().body(BadRequest.USER_NOT_EXISTS);
            case PatientUpdateType.Failure(BadRequest br) ->
                ResponseEntity.badRequest().body(br);
            case PatientUpdateType.Success(Patient patient) ->
                ResponseEntity.ok(PatientMapper.toPatientResponse(patient));
        };
    }
    
    public ResponseEntity<?> getAllPatients() {
        return ResponseEntity.ok(
            PatientMapper.toPatientResponse(
                this.patientService.getAllPatients()
            )
       );
    }
    
    public ResponseEntity<?> getPatient(Long id) {
        SearchType<Patient> response = this.patientService.getPatient(id);
        return switch (response) {
            case SearchType.Found(Patient patient) -> ResponseEntity.ok(PatientMapper.toPatientResponse(patient));
            case SearchType.Failed() -> ResponseEntity.notFound().build();
        };
    }
}
