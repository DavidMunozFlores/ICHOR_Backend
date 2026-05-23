package com.erguidos.ichor.service.coordinator;

import java.util.List;

import com.erguidos.ichor.dto.types.CoordinatorSearchResponseType;
import com.erguidos.ichor.entity.Coordinator;

public interface CoordinatorServiceInterface {
    List<Coordinator> getAllCoordinators();

    CoordinatorSearchResponseType getCoordinator(Long id);
}
