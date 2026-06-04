package com.erguidos.ichor.controller.patients;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erguidos.ichor.annotations.AuthenticatedPayload;
import com.erguidos.ichor.dto.mappers.PatientMapper;
import com.erguidos.ichor.dto.request.PatientCreationRequest;
import com.erguidos.ichor.dto.request.PatientUpdateRequest;
import com.erguidos.ichor.dto.response.PatientResponse;
import com.erguidos.ichor.dto.types.PatientCreationType;
import com.erguidos.ichor.dto.types.PatientUpdateType;
import com.erguidos.ichor.entity.Patient;
import com.erguidos.ichor.enums.BadRequest;
import com.erguidos.ichor.enums.Role;
import com.erguidos.ichor.service.patient.PatientServiceInterface;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/v1/patients")
@CrossOrigin(origins = {"${app.cors.allowed}"})
@Tag(name = "Patient Creation and Update", description = "Endpoints for creating and updating patients")
public class PatientSetController {
    private final PatientServiceInterface patientService;
    
    PatientSetController(PatientServiceInterface patientService) {
        this.patientService = patientService;
    }
    
    @Operation(summary = "Create a new patient record")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Patient successfully created",
            content = @Content(schema = @Schema(implementation = PatientResponse.class))
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Patient already exists"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request payload",
            content = @Content(schema = @Schema(implementation = BadRequest.class))
        )
    })
    @PostMapping("/create")
    public ResponseEntity<?> createPatient(
        @RequestBody @AuthenticatedPayload(Role.DOCTOR) PatientCreationRequest patientCreationRequest
    ) {
        PatientCreationType pct = this.patientService.createPatient(patientCreationRequest);
        return this.createPatientSwitch(pct);
    }
    
    private ResponseEntity<?> createPatientSwitch(PatientCreationType pct) {
        return switch (pct) {
            case PatientCreationType.Created(Patient patient) ->
                ResponseEntity.status(HttpStatus.CREATED).body(PatientMapper.toPatientResponse(patient));
            case PatientCreationType.Exists() ->
                ResponseEntity.status(HttpStatus.CONFLICT).build();
            case PatientCreationType.Failure(BadRequest br) ->
                ResponseEntity.badRequest().body(br);
        };
    }
    
    @Operation(summary = "Update a patient's height and weight")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Patient successfully updated",
            content = @Content(schema = @Schema(implementation = PatientResponse.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid request payload",
            content = @Content(schema = @Schema(implementation = BadRequest.class))
        )
    })
    @PatchMapping
    public ResponseEntity<?> updatePatient(
        @RequestBody @AuthenticatedPayload(Role.DOCTOR) PatientUpdateRequest patientUpdateRequest
    ) {
        PatientUpdateType put = this.patientService.updatePatient(patientUpdateRequest);
        return this.updatePatientSwitch(put);
    }
    
    private ResponseEntity<?> updatePatientSwitch(PatientUpdateType put) {
        return switch (put) {
            case PatientUpdateType.Failure(BadRequest br) ->
                ResponseEntity.badRequest().body(br);
            case PatientUpdateType.Success(Patient patient) ->
                ResponseEntity.ok(PatientMapper.toPatientResponse(patient));
        };
    }
}
