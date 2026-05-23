package com.erguidos.ichor.service.coordinator;

import java.util.List;
import java.util.Optional;

import com.erguidos.ichor.dto.types.CoordinatorSearchResponseType;
import com.erguidos.ichor.entity.Coordinator;
import com.erguidos.ichor.repository.CoordinatorRepository;

public class CoordinatorService
       implements CoordinatorServiceInterface {

    private final CoordinatorRepository coordinatorRepository;
    
    CoordinatorService(CoordinatorRepository coordinatorRepository) {
        this.coordinatorRepository = coordinatorRepository;
    }

    @Override
    public List<Coordinator> getAllCoordinators() {
        return this.coordinatorRepository.findAll();
    }

    @Override
    public CoordinatorSearchResponseType getCoordinator(Long id) {
        Optional<Coordinator> coordinatorOp = this.coordinatorRepository.getByUserId(id);
        if (coordinatorOp.isEmpty()) {
            return new CoordinatorSearchResponseType.Failed();
        }
        return new CoordinatorSearchResponseType.Found(coordinatorOp.get());
    }
}
