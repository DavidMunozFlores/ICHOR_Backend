package com.erguidos.ichor.init;

import com.erguidos.ichor.repository.UserRepository;
import org.springframework.stereotype.Component;

import com.erguidos.ichor.entity.Manager;
import com.erguidos.ichor.repository.ManagerRepository;

@Component
public class ManagerGenerator {
    private final ManagerRepository managerRepository;
    private final UserRepository userRepository;
    
    ManagerGenerator(ManagerRepository managerRepository, UserRepository userRepository) {
        this.managerRepository = managerRepository;
        this.userRepository = userRepository;
    }
    
    public void generate() {
        if (this.userRepository.findUserByUsername("managerCreator").isEmpty()) {
            Manager manager = Manager.builder()
                    .setUsername("managerCreator")
                    .setPassword("1234")
                    .build();
            
            this.managerRepository.save(manager);
        }
    }
}
