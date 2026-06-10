package com.erguidos.ichor.component;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.erguidos.ichor.dto.mappers.OrganPetitionMapper;
import com.erguidos.ichor.dto.response.OrganPetitionResponse;
import com.erguidos.ichor.entity.Doctor;
import com.erguidos.ichor.entity.OrganPetition;
import com.erguidos.ichor.entity.Patient;
import com.erguidos.ichor.enums.OrganType;
import com.erguidos.ichor.repository.DoctorRepository;
import com.erguidos.ichor.repository.OrganPetitionRepository;
import com.erguidos.ichor.repository.PatientRepository;

@Component
public class OrganPetitionGenerator {

    private static final Random RANDOM = new Random();

    private static final String[] HLAS = {
        "A:01:02 A:03:04 B:05:06 B:07:08 DRB1:09:10 DRB1:11:12",
        "A:01:02 A:02:03 B:01:02 B:02:03 DRB1:01:02 DRB1:02:03",
        "A:01:01 A:01:02 B:01:01 B:01:02 DRB1:01:01 DRB1:01:02",
        "A:01:01 A:02:02 B:01:01 B:02:02 DRB1:01:01 DRB1:02:02"
    };

    private final OrganPetitionRepository organPetitionRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public OrganPetitionGenerator(
            OrganPetitionRepository organPetitionRepository,
            PatientRepository patientRepository,
            DoctorRepository doctorRepository
    ) {
        this.organPetitionRepository = organPetitionRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    @Transactional
    public Set<OrganPetitionResponse> generate() {

        Set<OrganPetitionResponse> responses = new HashSet<>();

        List<Doctor> doctors = doctorRepository.findAll();

        for (int i = 0; i < 10; i++) {

            long patientId = RANDOM.nextLong(100) + 1;

            Patient patient = patientRepository.findById(patientId)
                    .orElseThrow();

            Doctor doctor = doctors.get(
                    RANDOM.nextInt(doctors.size())
            );

            OrganType organType = randomOrganType();

            double weight = RANDOM.nextDouble(200.0, 251.0);
            double volume = RANDOM.nextDouble(200.0, 251.0);

            String hlaString = HLAS[RANDOM.nextInt(HLAS.length)];

            OrganPetition op = OrganPetition.create(
                    organType,
                    weight,
                    volume,
                    HlaParser.parse(hlaString),
                    doctor,
                    patient
            );

            organPetitionRepository.save(op);
            op.accept();
            this.organPetitionRepository.save(op);

            responses.add(
                    OrganPetitionMapper.toOrganPetitionResponse(op)
            );
        }

        return responses;
    }

    private OrganType randomOrganType() {
        OrganType[] values = {
                OrganType.LEFT_LUNG,
                OrganType.RIGHT_LUNG,
                OrganType.LEFT_KIDNEY,
                OrganType.RIGHT_KIDNEY,
                OrganType.LIVER,
                OrganType.PANCREAS,
                OrganType.HEART,
                OrganType.INTESTINES
        };

        return values[RANDOM.nextInt(values.length)];
    }
}