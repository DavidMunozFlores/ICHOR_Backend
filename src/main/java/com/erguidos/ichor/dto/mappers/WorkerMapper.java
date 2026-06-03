package com.erguidos.ichor.dto.mappers;

import com.erguidos.ichor.dto.response.WorkerResponse;
import com.erguidos.ichor.entity.Coordinator;
import com.erguidos.ichor.entity.Doctor;
import com.erguidos.ichor.entity.User;

public class WorkerMapper {
    public static WorkerResponse toWorkerResponse(User user) {
        return switch(user) {
            case Doctor d -> WorkerMapper.toWorkerResponse(d);
            case Coordinator c -> WorkerMapper.toWorkerResponse(c);
            default -> throw new IllegalArgumentException("User is not a worker!");
        };
    }
    
    public static WorkerResponse toWorkerResponse(Doctor doctor) {
        return new WorkerResponse(
            doctor.getUsername(),
            doctor.getHospital().getName(),
            doctor.getRole()
        );
    }
    
    public static WorkerResponse toWorkerResponse(Coordinator coordinator) {
        return new WorkerResponse(
            coordinator.getUsername(),
            coordinator.getHospital().getName(),
            coordinator.getRole()
        );
    }
}
