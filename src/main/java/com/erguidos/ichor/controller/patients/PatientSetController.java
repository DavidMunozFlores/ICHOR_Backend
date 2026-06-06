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
import com.erguidos.ichor.dto.response.ErrorCodeResponse;
import com.erguidos.ichor.dto.response.PatientResponse;
import com.erguidos.ichor.entity.Patient;
import com.erguidos.ichor.enums.ErrorCode;
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
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PatientResponse.class))
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Patient already exists",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorCodeResponse.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request payload",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorCodeResponse.class))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Decryption failure",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorCodeResponse.class))
        ),
        @ApiResponse(
            responseCode = "401",
            description = "User not authorized"
        )
    })
    @PostMapping("/create")
    public ResponseEntity<PatientResponse> createPatient(
        @RequestBody @AuthenticatedPayload(Role.DOCTOR) PatientCreationRequest patientCreationRequest
    ) {
        Patient patient = this.patientService.createPatient(patientCreationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(PatientMapper.toPatientResponse(patient));
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
            content = @Content(schema = @Schema(implementation = ErrorCode.class))
        )
    })
    @PatchMapping
    public ResponseEntity<PatientResponse> updatePatient(
        @RequestBody @AuthenticatedPayload(Role.DOCTOR) PatientUpdateRequest patientUpdateRequest
    ) {
        Patient patient = this.patientService.updatePatient(patientUpdateRequest);
        return ResponseEntity.ok(PatientMapper.toPatientResponse(patient));
    }
}
