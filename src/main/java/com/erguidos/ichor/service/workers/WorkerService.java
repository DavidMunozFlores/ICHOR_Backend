package com.erguidos.ichor.service.workers;

import java.util.List;

import org.springframework.stereotype.Service;

import com.erguidos.ichor.entity.User;
import com.erguidos.ichor.enums.Role;
import com.erguidos.ichor.repository.UserRepository;

@Service
public class WorkerService implements WorkerServiceInterface {
    private final UserRepository userRepository;

    WorkerService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllWorkers() {
        return this.userRepository.findAll()
                .stream()
                .filter(u -> u.getRole() == Role.COORDINATOR || u.getRole() == Role.DOCTOR)
                .toList();
    }
}
