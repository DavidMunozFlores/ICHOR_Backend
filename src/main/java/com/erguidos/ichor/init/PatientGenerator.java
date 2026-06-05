package com.erguidos.ichor.init;

import com.erguidos.ichor.entity.Patient;
import com.erguidos.ichor.repository.PatientRepository;
import com.erguidos.ichor.utils.IchorUtils;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Component;

@Component
public class PatientGenerator {
    private static final int PATIENT_COUNT = 100;

    private final PatientRepository patientRepository;
    private final HospitalGenerator hospitalGenerator;

    public PatientGenerator(
            PatientRepository patientRepository,
            HospitalGenerator hospitalGenerator
    ) {
        this.patientRepository = patientRepository;
        this.hospitalGenerator = hospitalGenerator;
    }

    @Transactional
    public void generate() {
        if (patientRepository.count() > 0) return;

        try {
            for (String name : IchorUtils.generateFullNames(PATIENT_COUNT)) {
                Patient patient = Patient.builder()
                        .setInternalID(IchorUtils.randomInternalID())
                        .setName(name)
                        .setIdentification(IchorUtils.randomIdentification())
                        .setBloodType(IchorUtils.randomBloodType())
                        .setHeight(IchorUtils.randomHeight())
                        .setWeight(IchorUtils.randomWeight())
                        .setHospital(this.hospitalGenerator.getRandomHospital())
                        .build();
                patientRepository.save(patient);
            }
        } catch (BlankStringException | ImproperHeightException | ImproperWeightException e) {
            
        }
    }
}