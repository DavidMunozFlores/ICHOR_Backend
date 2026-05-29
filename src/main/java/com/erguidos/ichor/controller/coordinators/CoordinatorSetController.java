package com.erguidos.ichor.controller.coordinators;

import java.security.GeneralSecurityException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erguidos.ichor.dto.mappers.CoordinatorMapper;
import com.erguidos.ichor.dto.request.AuthenticatedRequest;
import com.erguidos.ichor.dto.request.CoordinatorCreateRequest;
import com.erguidos.ichor.dto.request.DecryptRequest;
import com.erguidos.ichor.entity.Coordinator;
import com.erguidos.ichor.enums.Role;
import com.erguidos.ichor.service.auth.AuthServiceInterface;
import com.erguidos.ichor.service.key.KeyServiceInterface;
import com.erguidos.ichor.types.CoordinatorCreationType;
import com.erguidos.ichor.service.coordinator.CoordinatorServiceInterface;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/coordinators")
@CrossOrigin(origins = {"${app.cors.allowed}"})
public class CoordinatorSetController {
    private final CoordinatorServiceInterface coordinatorService;
    private final KeyServiceInterface keyService;
    private final AuthServiceInterface authService;
    
    CoordinatorSetController(
        CoordinatorServiceInterface coordinatorService,
        KeyServiceInterface keyService,
        AuthServiceInterface authService
    ) {
        this.coordinatorService = coordinatorService;
        this.keyService = keyService;
        this.authService = authService;
    }
    
    @PostMapping("/create")
    public ResponseEntity<?> createCoordinator(@RequestBody DecryptRequest dr)
            throws JsonProcessingException, GeneralSecurityException {
        AuthenticatedRequest<CoordinatorCreateRequest> ar = this.keyService.decryptToAuthenticatedRequest(dr, CoordinatorCreateRequest.class);
        this.authService.authenticate(ar.authCredentials(), Role.MANAGER);
        CoordinatorCreationType ccrt = this.coordinatorService.createCoordinator(ar.data());
        
        return switch (ccrt) {
            case CoordinatorCreationType.Created(Coordinator coordinator) ->
                    ResponseEntity.status(HttpStatus.CREATED)
                        .body(CoordinatorMapper.toCoordinatorResponse(coordinator));
            case CoordinatorCreationType.Exists() ->
                    ResponseEntity.status(HttpStatus.CONFLICT).build();
            case CoordinatorCreationType.NoHospital(Long id) ->
                    ResponseEntity.badRequest().body(
                            String.format("Hospital with id %s doesn't exist", id));
        };
    }
}
