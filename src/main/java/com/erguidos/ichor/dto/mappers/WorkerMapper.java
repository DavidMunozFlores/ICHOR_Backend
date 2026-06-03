package com.erguidos.ichor.dto.mappers;

import com.erguidos.ichor.dto.response.WorkerResponse;
import com.erguidos.ichor.entity.Coordinator;
import com.erguidos.ichor.entity.Doctor;
import com.erguidos.ichor.entity.User;

public class WorkerMapper {
    public static WorkerResponse toWorkerResponse(User user) {
        return switch(user) {
            case Doctor d -> new WorkerResponse(d.getUsername(), d.getHospital().getName(), d.getRole());
            case Coordinator c -> new WorkerResponse(c.getUsername(), c.getHospital().getName(), c.getRole());
            default -> throw new IllegalArgumentException("User is not a worker!");
        };
    }
}
