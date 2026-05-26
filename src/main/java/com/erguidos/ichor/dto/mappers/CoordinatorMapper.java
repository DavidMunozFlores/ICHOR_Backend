package com.erguidos.ichor.dto.mappers;

import com.erguidos.ichor.dto.response.CoordinatorResponse;
import com.erguidos.ichor.entity.Coordinator;

public final class CoordinatorMapper {
    public static CoordinatorResponse toCoordinatorResponse(Coordinator coordinator) {
        return new CoordinatorResponse(
            coordinator.getId(),
            coordinator.getUsername(),
            coordinator.getHospital().getId()
        );
    }
}
