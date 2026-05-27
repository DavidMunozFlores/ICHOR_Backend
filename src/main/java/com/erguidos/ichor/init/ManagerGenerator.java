package com.erguidos.ichor.init;

import com.erguidos.ichor.repository.UserRepository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.erguidos.ichor.component.HashInterface;
import com.erguidos.ichor.entity.Manager;
import com.erguidos.ichor.repository.ManagerRepository;

@Component
public class ManagerGenerator {
    private final ManagerRepository managerRepository;
    private final UserRepository userRepository;
    private final HashInterface hashing;
    
    ManagerGenerator(
        ManagerRepository managerRepository,
        UserRepository userRepository,
        @Qualifier("bcryptPasswordEncoder") HashInterface hashing
    ) {
        this.managerRepository = managerRepository;
        this.userRepository = userRepository;
        this.hashing = hashing;
    }
    
    public void generate() {
        if (this.userRepository.findUserByUsername("managerCreator").isEmpty()) {
            Manager manager = Manager.builder()
                    .setUsername("managerCreator")
                    .setPassword(this.hashing.hashPassword("1234"))
                    .build();
            
            this.managerRepository.save(manager);
        }
    }
}
