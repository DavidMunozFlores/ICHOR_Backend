package com.erguidos.ichor.service.coordinator;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.erguidos.ichor.component.HashInterface;
import com.erguidos.ichor.dto.mappers.CoordinatorMapper;
import com.erguidos.ichor.dto.request.CoordinatorCreateRequest;
import com.erguidos.ichor.dto.response.CoordinatorResponse;
import com.erguidos.ichor.types.CoordinatorSearchType;
import com.erguidos.ichor.entity.Coordinator;
import com.erguidos.ichor.entity.Hospital;
import com.erguidos.ichor.entity.User;
import com.erguidos.ichor.enums.Role;
import com.erguidos.ichor.repository.CoordinatorRepository;
import com.erguidos.ichor.repository.HospitalRepository;
import com.erguidos.ichor.repository.UserRepository;
import com.erguidos.ichor.types.CoordinatorCreationType;

@Service
public class CoordinatorService
       implements CoordinatorServiceInterface {

    private final CoordinatorRepository coordinatorRepository;
    private final UserRepository userRepository;
    private final HospitalRepository hospitalRepository;
    private final HashInterface hashing;
    
    CoordinatorService(
            CoordinatorRepository coordinatorRepository,
            UserRepository userRepository,
            HospitalRepository hospitalRepository,
            @Qualifier("bcryptPasswordEncoder") HashInterface hashing
    ) {
        this.coordinatorRepository = coordinatorRepository;
        this.userRepository = userRepository;
        this.hospitalRepository = hospitalRepository;
        this.hashing = hashing;
    }

    @Override
    public List<CoordinatorResponse> getAllCoordinators() {
        return this.coordinatorRepository.findAll()
                .stream()
                .map(CoordinatorMapper::toCoordinatorResponse)
                .toList();
    }

    @Override
    public CoordinatorSearchType getCoordinator(Long id) {
        Optional<User> userOp = this.userRepository.findById(id);
        if (userOp.isEmpty()) { return new CoordinatorSearchType.Failed(); }
        User user = userOp.get();
        if (user.getRole() != Role.COORDINATOR) { return new CoordinatorSearchType.Failed(); }
        return new CoordinatorSearchType.Found((Coordinator) user);
    }

    @Override
    public CoordinatorCreationType createCoordinator(CoordinatorCreateRequest ccr) {
        Optional<User> user = this.userRepository.findUserByUsername(ccr.username());
        if (user.isPresent()) { return new CoordinatorCreationType.Exists(); }
        
        Optional<Hospital> hospital = this.hospitalRepository.findById(ccr.idHospital());
        if (hospital.isEmpty()) { return new CoordinatorCreationType.NoHospital(ccr.idHospital()); }
        
        Coordinator coordinator = Coordinator.builder()
                .setUsername(ccr.username())
                .setPassword(this.hashing.hashPassword(ccr.password()))
                .setHospital(hospital.get())
                .build();
        
        this.coordinatorRepository.save(coordinator);
        
        return new CoordinatorCreationType.Created(coordinator);
    }

    @Override
    public List<CoordinatorResponse> getCoordinatorsByName(String name) {
        return this.coordinatorRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(CoordinatorMapper::toCoordinatorResponse)
                .toList();
    }
}
