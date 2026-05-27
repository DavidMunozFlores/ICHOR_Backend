package com.erguidos.ichor.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Init implements CommandLineRunner {

    private final ManagerGenerator managerGenerator;
    private final HospitalGenerator hospitalGenerator;
    
    Init(
        ManagerGenerator managerGenerator,
        HospitalGenerator hospitalGenerator
    ) {
        this.managerGenerator = managerGenerator;
        this.hospitalGenerator = hospitalGenerator;
    }
    
    @Override
    public void run(String... args) {
        this.managerGenerator.generate();
        this.hospitalGenerator.generate();
    }
}
