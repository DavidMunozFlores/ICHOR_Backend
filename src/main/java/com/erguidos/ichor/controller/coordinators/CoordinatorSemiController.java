package com.erguidos.ichor.controller.coordinators;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.erguidos.ichor.controller.AuthenticatedSemiController;
import com.erguidos.ichor.dto.mappers.CoordinatorMapper;
import com.erguidos.ichor.dto.request.CoordinatorCreateRequest;
import com.erguidos.ichor.dto.request.DecryptRequest;
import com.erguidos.ichor.dto.types.CoordinatorCreationType;
import com.erguidos.ichor.dto.types.SearchType;
import com.erguidos.ichor.entity.Coordinator;
import com.erguidos.ichor.enums.Role;
import com.erguidos.ichor.service.auth.AuthServiceInterface;
import com.erguidos.ichor.service.coordinator.CoordinatorServiceInterface;
import com.erguidos.ichor.service.key.KeyServiceInterface;

@Component
public class CoordinatorSemiController extends AuthenticatedSemiController {
    private final CoordinatorServiceInterface coordinatorService;
    
    CoordinatorSemiController(
        KeyServiceInterface keyService,
        AuthServiceInterface authService,
        CoordinatorServiceInterface coordinatorService
    ) {
        super(keyService, authService);
        this.coordinatorService = coordinatorService;
    }
    
    public ResponseEntity<?> createCoordinator(DecryptRequest dr) {
        return this.process(
                dr,
                CoordinatorCreateRequest.class,
                Role.COORDINATOR,
                this::createWithData
        );
    }
    
    private ResponseEntity<?> createWithData(CoordinatorCreateRequest data) {
        CoordinatorCreationType ccrt = this.coordinatorService.createCoordinator(data);
        return switch (ccrt) {
            case CoordinatorCreationType.Created(Coordinator coordinator) ->
                ResponseEntity.status(HttpStatus.CREATED).body(CoordinatorMapper.toCoordinatorResponse(coordinator));
            case CoordinatorCreationType.Exists() ->
                ResponseEntity.status(HttpStatus.CONFLICT).build();
            case CoordinatorCreationType.NoHospital(Long id) ->
                ResponseEntity.badRequest().body(String.format("Hospital with id %s doesn't exist", id));
        };
    }
    
    public ResponseEntity<?> getAllCoordinators() {
        return ResponseEntity.ok(
            CoordinatorMapper.toCoordinatorResponse(
                this.coordinatorService.getAllCoordinators()
            )
       );
    }
    
    public ResponseEntity<?> getCoordinator(Long id) {
        SearchType<Coordinator> response = this.coordinatorService.getCoordinator(id);
        
        return switch (response) {
            case SearchType.Found(Coordinator coordinator) ->
                ResponseEntity.ok(CoordinatorMapper.toCoordinatorResponse(coordinator));
            case SearchType.Failed() ->
                ResponseEntity.notFound().build();
        };
    }
    
    public ResponseEntity<?> getCoordinatorsByName(String name) {
        return ResponseEntity.ok(
            CoordinatorMapper.toCoordinatorResponse(
                this.coordinatorService.getCoordinatorsByName(name)
            )
       );
    }
}
