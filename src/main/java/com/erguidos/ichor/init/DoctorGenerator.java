package com.erguidos.ichor.init;

import com.erguidos.ichor.component.HashInterface;
import com.erguidos.ichor.entity.Doctor;
import com.erguidos.ichor.entity.Hospital;
import com.erguidos.ichor.repository.DoctorRepository;
import com.erguidos.ichor.repository.HospitalRepository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DoctorGenerator {

    private final DoctorRepository doctorRepository;
    private final HospitalRepository hospitalRepository;
    private final HashInterface hashing;

    public DoctorGenerator(
            DoctorRepository doctorRepository,
            HospitalRepository hospitalRepository,
            @Qualifier("bcryptPasswordEncoder") HashInterface hashing
    ) {
        this.doctorRepository = doctorRepository;
        this.hospitalRepository = hospitalRepository;
        this.hashing = hashing;
    }

    public void generate() {
        Hospital hospital = hospitalRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No hospitals found in DB"));

        List<String> doctorNames = List.of("Doctor", "Pepe", "Ana", "Lepan", "Ichor");

        for (String name : doctorNames) {
            if (doctorRepository.existsByUsername(name)) continue;

            Doctor doctor = Doctor.create(
                    name,
                    hashing.hashPassword("1234"),
                    hospital
            );

            
            doctorRepository.save(doctor);
        }
    }
}