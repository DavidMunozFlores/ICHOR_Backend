package com.erguidos.ichor.controller.coordinators;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.erguidos.ichor.dto.response.CoordinatorResponseDTO;
import com.erguidos.ichor.dto.types.CoordinatorSearchResponseType;
import com.erguidos.ichor.entity.Coordinator;
import com.erguidos.ichor.service.coordinator.CoordinatorServiceInterface;


@RestController
@RequestMapping("/api/v1/coordinators")
public class CoordinatorGetController {
    private final CoordinatorServiceInterface coordinatorService;
    
    public CoordinatorGetController(CoordinatorServiceInterface coordinatorService) {
        this.coordinatorService = coordinatorService;
    }

    @GetMapping
    public ResponseEntity<List<CoordinatorResponseDTO>> getAllCoordinators() {
        return ResponseEntity.ok(
                    this.coordinatorService.getAllCoordinators()
                    .stream()
                    .map(CoordinatorResponseDTO::of)
                    .toList()
               );
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getCoordinator(@RequestParam Long id) {
        CoordinatorSearchResponseType response = this.coordinatorService.getCoordinator(id);
        return switch (response) {
            case CoordinatorSearchResponseType.Found(Coordinator coordinator) ->
                    ResponseEntity.ok(CoordinatorResponseDTO.of(coordinator));
            case CoordinatorSearchResponseType.Failed f -> ResponseEntity.notFound().build();
        };
    }
    
}
