package com.erguidos.ichor.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erguidos.ichor.entity.Patient;


@Repository
public interface PatientRepository
         extends JpaRepository<Patient, Long> {
    Optional<Patient> getByInternalID(String internalID);

    Optional<Patient> getByIdentification(String identification);
}
