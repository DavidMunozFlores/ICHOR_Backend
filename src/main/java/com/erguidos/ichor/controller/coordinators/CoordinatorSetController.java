package com.erguidos.ichor.controller.coordinators;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erguidos.ichor.dto.mappers.CoordinatorMapper;
import com.erguidos.ichor.dto.request.CoordinatorCreateRequest;
import com.erguidos.ichor.dto.response.CoordinatorResponse;
import com.erguidos.ichor.dto.types.CoordinatorCreationType;
import com.erguidos.ichor.entity.Coordinator;
import com.erguidos.ichor.enums.Role;
import com.erguidos.ichor.pre.AuthenticatedPayload;
import com.erguidos.ichor.service.coordinator.CoordinatorServiceInterface;

import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/coordinators")
@CrossOrigin(origins = {"${app.cors.allowed}"})
public class CoordinatorSetController {
    private final CoordinatorServiceInterface coordinatorService;
    
    CoordinatorSetController(CoordinatorServiceInterface coordinatorService) {
        this.coordinatorService = coordinatorService;
    }
    
    @PostMapping("/create")
    public ResponseEntity<CoordinatorResponse> createCoordinator(
        @RequestBody @AuthenticatedPayload(Role.MANAGER) CoordinatorCreateRequest coordinatorCreateRequest
    ) {
        CoordinatorCreationType cct = this.coordinatorService.createCoordinator(coordinatorCreateRequest);
        return this.createCoordinatorSwitch(cct);
    }
    
    private ResponseEntity<CoordinatorResponse> createCoordinatorSwitch(CoordinatorCreationType cct) {
        return switch (cct) {
            case CoordinatorCreationType.Created(Coordinator coordinator) ->
                ResponseEntity.status(HttpStatus.CREATED).body(CoordinatorMapper.toCoordinatorResponse(coordinator));
            case CoordinatorCreationType.Exists() ->
                ResponseEntity.status(HttpStatus.CONFLICT).build();
            case CoordinatorCreationType.NoHospital() ->
                ResponseEntity.badRequest().build();
        };
    }
}
