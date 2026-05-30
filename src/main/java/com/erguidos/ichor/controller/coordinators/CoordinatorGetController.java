package com.erguidos.ichor.controller.coordinators;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.erguidos.ichor.dto.mappers.CoordinatorMapper;
import com.erguidos.ichor.dto.response.CoordinatorResponse;
import com.erguidos.ichor.entity.Coordinator;
import com.erguidos.ichor.service.coordinator.CoordinatorServiceInterface;
import com.erguidos.ichor.types.CoordinatorSearchType;

@RestController
@RequestMapping("/api/v1/coordinators")
@CrossOrigin(origins = {"${app.cors.allowed}"})
public class CoordinatorGetController {
    private final CoordinatorServiceInterface coordinatorService;
    
    public CoordinatorGetController(CoordinatorServiceInterface coordinatorService) {
        this.coordinatorService = coordinatorService;
    }

    @GetMapping
    public ResponseEntity<List<CoordinatorResponse>> getAllCoordinators() {
        return ResponseEntity.ok(this.coordinatorService.getAllCoordinators());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CoordinatorResponse> getCoordinator(@RequestParam Long id) {
        CoordinatorSearchType response = this.coordinatorService.getCoordinator(id);
        return switch (response) {
            case CoordinatorSearchType.Found(Coordinator coordinator) ->
                    ResponseEntity.ok(CoordinatorMapper.toCoordinatorResponse(coordinator));
            case CoordinatorSearchType.Failed() -> ResponseEntity.notFound().build();
        };
    }
    
    @GetMapping("/get-by-name/{name}")
    public ResponseEntity<List<CoordinatorResponse>> getCoordinatorsByName(@RequestParam String name) {
        return ResponseEntity.ok(this.coordinatorService.getCoordinatorsByName(name));
    }
}
