package com.erguidos.ichor.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Init implements CommandLineRunner {

    private final ManagerGenerator managerGenerator;
    private final HospitalGenerator hospitalGenerator;
    private final DoctorGenerator doctorGenerator;
    private final PatientGenerator patientGenerator;
    
    Init(
        ManagerGenerator managerGenerator,
        HospitalGenerator hospitalGenerator,
        DoctorGenerator doctorGenerator,
        PatientGenerator patientGenerator
    ) {
        this.managerGenerator = managerGenerator;
        this.hospitalGenerator = hospitalGenerator;
        this.doctorGenerator = doctorGenerator;
        this.patientGenerator = patientGenerator;
    }
    
    @Override
    public void run(String... args) {
        this.managerGenerator.generate();
        this.hospitalGenerator.generate();
        this.doctorGenerator.generate();
        this.patientGenerator.generate();
    }
}
