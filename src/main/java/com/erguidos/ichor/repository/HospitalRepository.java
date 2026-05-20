package com.erguidos.ichor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erguidos.ichor.entity.Hospital;

@Repository
public interface HospitalRepository
         extends JpaRepository<Hospital, Long> {}
