package com.erguidos.ichor.dto.mappers;

import java.util.List;

import com.erguidos.ichor.dto.response.CoordinatorResponse;
import com.erguidos.ichor.entity.Coordinator;

public final class CoordinatorMapper {
    private CoordinatorMapper() {
        throw new UnsupportedOperationException("Don't instantiate CoordinatorMapper");
    }
    
    public static CoordinatorResponse toCoordinatorResponse(Coordinator coordinator) {
        return new CoordinatorResponse(
            coordinator.getId(),
            coordinator.getUsername(),
            coordinator.getHospital().getId()
        );
    }
    
    public static List<CoordinatorResponse> toCoordinatorResponse(List<Coordinator> coordinators) {
        return coordinators.stream()
                .map(CoordinatorMapper::toCoordinatorResponse)
                .toList();
    }
}
