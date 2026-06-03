package com.erguidos.ichor.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erguidos.ichor.entity.Patient;
import com.erguidos.ichor.enums.BloodType;


@Repository
public interface PatientRepository
         extends JpaRepository<Patient, Long> {
    Optional<Patient> getByInternalID(String internalID);

    Optional<Patient> getByIdentification(String identification);
    
    Set<Patient> findByBloodTypeIn(List<BloodType> bloodTypes);
}
