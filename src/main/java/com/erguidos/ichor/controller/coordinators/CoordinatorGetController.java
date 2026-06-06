package com.erguidos.ichor.controller.coordinators;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erguidos.ichor.dto.mappers.CoordinatorMapper;
import com.erguidos.ichor.dto.mappers.ListMapper;
import com.erguidos.ichor.dto.response.CoordinatorResponse;
import com.erguidos.ichor.dto.response.ListWrapper;
import com.erguidos.ichor.entity.Coordinator;
import com.erguidos.ichor.service.coordinator.CoordinatorServiceInterface;

@RestController
@RequestMapping("/api/v1/coordinators")
@CrossOrigin(origins = {"${app.cors.allowed}"})
public class CoordinatorGetController {
    private final CoordinatorServiceInterface coordinatorService;
    
    public CoordinatorGetController(CoordinatorServiceInterface coordinatorService) {
        this.coordinatorService = coordinatorService;
    }

    @GetMapping
    public ResponseEntity<ListWrapper<CoordinatorResponse>> getAllCoordinators() {
        return ResponseEntity.ok(
            ListMapper.toListWrapper(
                this.coordinatorService.getAllCoordinators(),
                CoordinatorMapper::toCoordinatorResponse
            )
        );
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CoordinatorResponse> getCoordinator(@PathVariable Long id) {
        Coordinator coordinator = this.coordinatorService.getCoordinator(id);
        return ResponseEntity.ok(CoordinatorMapper.toCoordinatorResponse(coordinator));
    }
    
    @GetMapping("/get-by-name/{name}")
    public ResponseEntity<ListWrapper<CoordinatorResponse>> getCoordinatorsByName(@PathVariable String name) {
        return ResponseEntity.ok(
            ListMapper.toListWrapper(
                this.coordinatorService.getCoordinatorsByName(name),
                CoordinatorMapper::toCoordinatorResponse
            )
        );
    }
}
