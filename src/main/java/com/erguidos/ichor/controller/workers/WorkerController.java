package com.erguidos.ichor.controller.workers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erguidos.ichor.dto.mappers.ListMapper;
import com.erguidos.ichor.dto.mappers.WorkerMapper;
import com.erguidos.ichor.dto.response.ListWrapper;
import com.erguidos.ichor.dto.response.WorkerResponse;
import com.erguidos.ichor.service.workers.WorkerServiceInterface;

@RestController
@RequestMapping("/api/v1/workers")
@CrossOrigin(origins = {"${app.cors.allowed}"})
public class WorkerController {
    private final WorkerServiceInterface workerService;
    
    WorkerController(WorkerServiceInterface workerService) {
        this.workerService = workerService;
    }
    
    @GetMapping
    public ResponseEntity<ListWrapper<WorkerResponse>> getAllWorkers() {
        return ResponseEntity.ok(
            ListMapper.toListWrapper(
                this.workerService.getAllWorkers(),
                WorkerMapper::toWorkerResponse
            )
        );
    }
}
