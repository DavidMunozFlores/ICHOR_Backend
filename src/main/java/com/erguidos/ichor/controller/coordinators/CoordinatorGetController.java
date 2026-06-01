package com.erguidos.ichor.controller.coordinators;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/coordinators")
@CrossOrigin(origins = {"${app.cors.allowed}"})
public class CoordinatorGetController {
    private final CoordinatorSemiController semi;
    
    public CoordinatorGetController(CoordinatorSemiController semi) {
        this.semi = semi;
    }

    @GetMapping
    public ResponseEntity<?> getAllCoordinators() {
        return this.semi.getAllCoordinators();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getCoordinator(@RequestParam Long id) {
        return this.semi.getCoordinator(id);
    }
    
    @GetMapping("/get-by-name/{name}")
    public ResponseEntity<?> getCoordinatorsByName(@PathVariable String name) {
        return this.semi.getCoordinatorsByName(name);
    }
}
