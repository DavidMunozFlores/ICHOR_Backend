package com.erguidos.ichor.service.workers;

import java.util.List;

import com.erguidos.ichor.entity.User;

public interface WorkerServiceInterface {
    List<User> getAllWorkers();
    User findWorkerByUsername(String username);
}
