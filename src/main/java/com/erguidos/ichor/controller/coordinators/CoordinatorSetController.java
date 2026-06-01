package com.erguidos.ichor.controller.coordinators;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erguidos.ichor.dto.request.DecryptRequest;

import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/coordinators")
@CrossOrigin(origins = {"${app.cors.allowed}"})
public class CoordinatorSetController {
    private final CoordinatorSemiController semi;
    
    CoordinatorSetController(CoordinatorSemiController semi) {
        this.semi = semi;
    }
    
    @PostMapping("/create")
    public ResponseEntity<?> createCoordinator(@RequestBody DecryptRequest dr) {
        return this.semi.createCoordinator(dr);
    }
}
