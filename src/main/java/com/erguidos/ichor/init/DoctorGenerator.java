package com.erguidos.ichor.init;

import com.erguidos.ichor.component.HashInterface;
import com.erguidos.ichor.entity.Doctor;
import com.erguidos.ichor.repository.DoctorRepository;
import com.erguidos.ichor.utils.IchorUtils;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class DoctorGenerator {
    private static final int DOCTOR_COUNT = 20;
    private static final String DEFAULT_PASSWORD = "1234Abc@";

    private final DoctorRepository doctorRepository;
    private final HospitalGenerator hospitalGenerator;
    private final HashInterface hashing;

    public DoctorGenerator(
            DoctorRepository doctorRepository,
            HospitalGenerator hospitalGenerator,
            @Qualifier("bcryptPasswordEncoder") HashInterface hashing
    ) {
        this.doctorRepository = doctorRepository;
        this.hospitalGenerator = hospitalGenerator;
        this.hashing = hashing;
    }

    public void generate() {
        Doctor basicDoctor = Doctor.create(
                "doctor",
                this.hashing.hashPassword("1234"),
                this.hospitalGenerator.getRandomHospital()
        );
        
        this.doctorRepository.save(basicDoctor);
        
        for (String name : IchorUtils.generateFullNames(DOCTOR_COUNT)) {
            if (doctorRepository.existsByUsername(name)) continue;

            Doctor doctor = Doctor.create(
                    name,
                    hashing.hashPassword(DEFAULT_PASSWORD),
                    this.hospitalGenerator.getRandomHospital()
            );
            
            doctorRepository.save(doctor);
        }
    }
}