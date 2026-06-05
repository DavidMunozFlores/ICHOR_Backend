package com.erguidos.ichor.service.coordinator;

import java.util.List;

import com.erguidos.ichor.dto.request.CoordinatorCreateRequest;
import com.erguidos.ichor.dto.types.SearchType;
import com.erguidos.ichor.entity.Coordinator;

public interface CoordinatorServiceInterface {
    List<Coordinator> getAllCoordinators();

    SearchType<Coordinator> getCoordinator(Long id);

    Coordinator createCoordinator(CoordinatorCreateRequest ccr);

    List<Coordinator> getCoordinatorsByName(String name);
}
