package com.erguidos.ichor.service.coordinator;

import java.util.List;

import com.erguidos.ichor.dto.request.CoordinatorCreateRequest;
import com.erguidos.ichor.dto.response.CoordinatorResponse;
import com.erguidos.ichor.types.CoordinatorSearchType;
import com.erguidos.ichor.types.CoordinatorCreationType;

public interface CoordinatorServiceInterface {
    List<CoordinatorResponse> getAllCoordinators();

    CoordinatorSearchType getCoordinator(Long id);

    CoordinatorCreationType createCoordinator(CoordinatorCreateRequest ccr);
}
