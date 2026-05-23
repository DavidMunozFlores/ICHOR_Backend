package com.erguidos.ichor.dto.response;

import com.erguidos.ichor.entity.Coordinator;

public record CoordinatorResponseDTO(
    Long id,
    String username,
    Long hospitalId
) {
    public static CoordinatorResponseDTO of(Coordinator coordinator) {
        return new CoordinatorResponseDTO(
            coordinator.getId(),
            coordinator.getUsername(),
            coordinator.getHospital().getId()
        );
    }
}
