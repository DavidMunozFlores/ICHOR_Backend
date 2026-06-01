package com.erguidos.ichor.service.coordinator;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.erguidos.ichor.component.HashInterface;
import com.erguidos.ichor.dto.request.CoordinatorCreateRequest;
import com.erguidos.ichor.dto.types.CoordinatorCreationType;
import com.erguidos.ichor.dto.types.SearchType;
import com.erguidos.ichor.entity.Coordinator;
import com.erguidos.ichor.entity.Hospital;
import com.erguidos.ichor.entity.User;
import com.erguidos.ichor.enums.Role;
import com.erguidos.ichor.repository.CoordinatorRepository;
import com.erguidos.ichor.repository.HospitalRepository;
import com.erguidos.ichor.repository.UserRepository;

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
    public List<Coordinator> getAllCoordinators() {
        return this.coordinatorRepository.findAll();
    }

    @Override
    public SearchType<Coordinator> getCoordinator(Long id) {
        Optional<User> userOp = this.userRepository.findById(id);
        if (userOp.isEmpty()) { return new SearchType.Failed<Coordinator>(); }
        User user = userOp.get();
        if (user.getRole() != Role.COORDINATOR) { return new SearchType.Failed<Coordinator>(); }
        return new SearchType.Found<Coordinator>((Coordinator) user);
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
    public List<Coordinator> getCoordinatorsByName(String name) {
        return this.coordinatorRepository.findByUsernameContainingIgnoreCase(name);
    }
}
