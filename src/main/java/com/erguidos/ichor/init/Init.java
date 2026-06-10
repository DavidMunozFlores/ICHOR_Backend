package com.erguidos.ichor.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Init implements CommandLineRunner {
    private final ManagerGenerator managerGenerator;
    private final HospitalGenerator hospitalGenerator;
    private final DoctorGenerator doctorGenerator;
    private final CoordinatorGenerator coordinatorGenerator;
    private final PatientGenerator patientGenerator;
    
    Init(
        ManagerGenerator managerGenerator,
        HospitalGenerator hospitalGenerator,
        DoctorGenerator doctorGenerator,
        CoordinatorGenerator coordinatorGenerator,
        PatientGenerator patientGenerator
    ) {
        this.managerGenerator = managerGenerator;
        this.hospitalGenerator = hospitalGenerator;
        this.doctorGenerator = doctorGenerator;
        this.coordinatorGenerator = coordinatorGenerator;
        this.patientGenerator = patientGenerator;
    }
    
    @Override
    public void run(String... args) {
        this.managerGenerator.generate();
        this.hospitalGenerator.generate();
        this.doctorGenerator.generate();
        this.coordinatorGenerator.generate();
        this.patientGenerator.generate();
    }
}
