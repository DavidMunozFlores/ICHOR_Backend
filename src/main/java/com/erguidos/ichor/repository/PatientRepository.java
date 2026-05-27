package com.erguidos.ichor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erguidos.ichor.entity.Patient;

public interface PatientRepository
         extends JpaRepository<Patient, Long> {
}
