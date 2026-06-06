package com.erguidos.ichor.service.workers;

import java.util.List;

import org.springframework.stereotype.Service;

import com.erguidos.ichor.entity.User;
import com.erguidos.ichor.repository.WorkerRepository;

@Service
public class WorkerService implements WorkerServiceInterface {
    private final WorkerRepository workerRepository;

    WorkerService(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    @Override
    public List<User> getAllWorkers() {
        return this.workerRepository.findCoordinatorsAndDoctors();
    }
}
