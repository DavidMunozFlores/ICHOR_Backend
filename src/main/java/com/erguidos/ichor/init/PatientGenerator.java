package com.erguidos.ichor.init;

import com.erguidos.ichor.entity.Hospital;
import com.erguidos.ichor.entity.Patient;
import com.erguidos.ichor.enums.BloodType;
import com.erguidos.ichor.exceptions.BlankStringException;
import com.erguidos.ichor.exceptions.ImproperHeightException;
import com.erguidos.ichor.exceptions.ImproperWeightException;
import com.erguidos.ichor.repository.HospitalRepository;
import com.erguidos.ichor.repository.PatientRepository;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
public class PatientGenerator {

    private final PatientRepository patientRepository;
    private final HospitalRepository hospitalRepository;

    private final Random random = new Random();

    public PatientGenerator(
            PatientRepository patientRepository,
            HospitalRepository hospitalRepository
    ) {
        this.patientRepository = patientRepository;
        this.hospitalRepository = hospitalRepository;
    }

    @Transactional
    public void generate() {
        if (patientRepository.count() > 0) return;

        List<Hospital> hospitals = hospitalRepository.findAll();
        if (hospitals.isEmpty()) {
            throw new RuntimeException("No hospitals in DB");
        }

        List<String> names = List.of(
                "Juan", "Maria", "Pedro", "Lucia", "Carlos",
                "Ana", "David", "Elena", "Miguel", "Sofia"
        );

        List<BloodType> bloodTypes = List.of(BloodType.values());

        for (int i = 0; i < 10; i++) {

            String name = names.get(i);
            BloodType bloodType = bloodTypes.get(random.nextInt(bloodTypes.size()));
            Hospital hospital = hospitals.get(0);//random.nextInt(hospitals.size())

            try {
                Patient patient = Patient.builder()
                        .setInternalID("INT-" + UUID.randomUUID().toString().substring(0, 8))
                        .setName(name)
                        .setIdentification("ID-" + (100000 + i))
                        .setBloodType(bloodType)
                        .setHeight(150 + random.nextDouble() * 40)   // 150–190 cm
                        .setWeight(50 + random.nextDouble() * 50)    // 50–100 kg
                        .setHospital(hospital)
                        .build();
                

                patientRepository.save(patient);
            } catch (BlankStringException | ImproperHeightException | ImproperWeightException e) {
                
            }

        }
    }
}