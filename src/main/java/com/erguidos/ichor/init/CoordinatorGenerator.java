package com.erguidos.ichor.init;

import com.erguidos.ichor.component.HashInterface;
import com.erguidos.ichor.entity.Coordinator;
import com.erguidos.ichor.repository.CoordinatorRepository;
import com.erguidos.ichor.repository.UserRepository;
import com.erguidos.ichor.utils.IchorUtils;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class CoordinatorGenerator {
    private static final int COORDINATOR_COUNT = 20;

    private final CoordinatorRepository coordinatorRepository;
    private final UserRepository userRepository;
    private final HospitalGenerator hospitalGenerator;
    private final HashInterface hashing;

    public CoordinatorGenerator(
            CoordinatorRepository coordinatorRepository,
            UserRepository userRepository,
            HospitalGenerator hospitalGenerator,
            @Qualifier("bcryptPasswordEncoder") HashInterface hashing
    ) {
        this.coordinatorRepository = coordinatorRepository;
        this.userRepository = userRepository;
        this.hospitalGenerator = hospitalGenerator;
        this.hashing = hashing;
    }

    public void generate() {
        Coordinator basicCoordinator = Coordinator.builder()
                .setUsername("coordinator")
                .setPassword(this.hashing.hashPassword("1234"))
                .setHospital(this.hospitalGenerator.getRandomHospital())
                .build();
        
        this.coordinatorRepository.save(basicCoordinator);
        
        for (String name : IchorUtils.generateFullNames(COORDINATOR_COUNT)) {
            if (this.userRepository.findUserByUsername(name).isPresent()) continue;

            Coordinator coordinator = Coordinator.builder()
                    .setUsername(name)
                    .setPassword(this.hashing.hashPassword("1234"))
                    .setHospital(this.hospitalGenerator.getRandomHospital())
                    .build();
            
            this.coordinatorRepository.save(coordinator);
        }
    }
}