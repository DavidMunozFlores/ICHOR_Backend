package com.erguidos.ichor.service.coordinator;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erguidos.ichor.component.HashInterface;
import com.erguidos.ichor.dto.request.CoordinatorCreateRequest;
import com.erguidos.ichor.entity.Coordinator;
import com.erguidos.ichor.entity.Hospital;
import com.erguidos.ichor.entity.User;
import com.erguidos.ichor.enums.Role;
import com.erguidos.ichor.error.Errors;
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
    public Coordinator getCoordinator(Long id) {
        Optional<User> userOp = this.userRepository.findById(id);
        if (userOp.isEmpty()) { throw Errors.User.NOT_EXISTS.asException(); }
        User user = userOp.get();
        if (user.getRole() != Role.COORDINATOR) { throw Errors.User.WRONG_ROLE.asException(); }
        return (Coordinator) user;
    }

    @Override
    @Transactional
    public Coordinator createCoordinator(CoordinatorCreateRequest ccr) {
        Optional<User> user = this.userRepository.findUserByUsername(ccr.username());
        if (user.isPresent()) { throw Errors.User.ALREADY_EXISTS.asException(); }
        
        Hospital hospital = this.hospitalRepository.findById(ccr.idHospital())
                                                   .orElseThrow(Errors.Hospital.NOT_EXISTS.asSupplier());
        
        Coordinator coordinator = Coordinator.builder()
                .setUsername(ccr.username())
                .setPassword(this.hashing.hashPassword(ccr.password()))
                .setHospital(hospital)
                .build();
        
        this.coordinatorRepository.save(coordinator);
        
        return coordinator;
    }

    @Override
    public List<Coordinator> getCoordinatorsByName(String name) {
        return this.coordinatorRepository.findByUsernameContainingIgnoreCase(name);
    }
}
