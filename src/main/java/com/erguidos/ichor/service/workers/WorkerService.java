package com.erguidos.ichor.service.workers;

import com.erguidos.ichor.repository.UserRepository;
import java.util.List;

import org.springframework.stereotype.Service;

import com.erguidos.ichor.entity.User;
import com.erguidos.ichor.error.Errors;
import com.erguidos.ichor.repository.WorkerRepository;

@Service
public class WorkerService implements WorkerServiceInterface {
    private final UserRepository userRepository;
    private final WorkerRepository workerRepository;

    WorkerService(WorkerRepository workerRepository, UserRepository userRepository) {
        this.workerRepository = workerRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllWorkers() {
        return this.workerRepository.findCoordinatorsAndDoctors();
    }

    @Override
    public User findWorkerByUsername(String username) {
        return this.userRepository
                   .findUserByUsername(username)
                   .orElseThrow(Errors.User.NOT_EXISTS.asSupplier());
    }
}
