package com.erguidos.ichor.service.coordinator;

import java.util.List;

import com.erguidos.ichor.dto.request.CoordinatorCreateRequest;
import com.erguidos.ichor.types.CoordinatorSearchType;
import com.erguidos.ichor.types.CoordinatorCreationType;
import com.erguidos.ichor.entity.Coordinator;

public interface CoordinatorServiceInterface {
    List<Coordinator> getAllCoordinators();

    CoordinatorSearchType getCoordinator(Long id);

    CoordinatorCreationType createCoordinator(CoordinatorCreateRequest ccr);
}
