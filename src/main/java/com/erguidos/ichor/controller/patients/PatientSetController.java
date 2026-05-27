package com.erguidos.ichor.controller.patients;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erguidos.ichor.component.AuthenticatedManager;
import com.erguidos.ichor.dto.request.DecryptRequest;
import com.erguidos.ichor.dto.request.PatientCreationRequest;
import com.erguidos.ichor.dto.request.PatientUpdateRequest;
import com.erguidos.ichor.dto.response.PatientResponse;
import com.erguidos.ichor.enums.Role;
import com.erguidos.ichor.service.patient.PatientServiceInterface;
import com.erguidos.ichor.service.patient.PatientUpdateType;
import com.erguidos.ichor.types.PatientCreationType;


@RestController
@RequestMapping("/api/v1/patients")
@CrossOrigin(origins = {"${app.cors.allowed}"})
public class PatientSetController {
    private final PatientServiceInterface patientService;
    private final AuthenticatedManager manager;
    
    PatientSetController(
        PatientServiceInterface patientService,
        AuthenticatedManager manager
    ) {
        this.patientService = patientService;
        this.manager = manager;
    }
    
    @PostMapping("/create")
    public ResponseEntity<?> createPatient(@RequestBody DecryptRequest dr) {
        return this.manager.work(
            dr,
            PatientCreationRequest.class,
            Role.DOCTOR,
            this.patientService::createPatient,
            r -> switch (r) {
                case PatientCreationType.Created(PatientResponse pr) ->
                    ResponseEntity.status(HttpStatus.CREATED).body(pr);
                case PatientCreationType.Exists() ->
                    ResponseEntity.status(HttpStatus.CONFLICT).build();
                case PatientCreationType.NoHospital() ->
                    ResponseEntity.badRequest().body("HOSPITAL_NOT_EXISTS");
                case PatientCreationType.BadBlood() ->
                    ResponseEntity.badRequest().body("BLOOD_TYPE_NOT_EXISTS");
            }
        );
    }
    
    @PatchMapping
    public ResponseEntity<?> updatePatient(@RequestBody DecryptRequest dr) {
        return this.manager.work(
            dr,
            PatientUpdateRequest.class,
            Role.DOCTOR,
            this.patientService::updatePatient,
            r -> switch (r) {
                case PatientUpdateType.NotExists() ->
                    ResponseEntity.badRequest().body("USER_NOT_EXISTS");
                case PatientUpdateType.Failure() ->
                    ResponseEntity.badRequest().build();
                case PatientUpdateType.Success(PatientResponse pr) ->
                    ResponseEntity.ok(pr);
            }
        );
    }
}
