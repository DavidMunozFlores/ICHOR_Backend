package com.erguidos.ichor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erguidos.ichor.entity.OrganPetition;

@Repository
public interface OrganPetitionRepository extends JpaRepository<OrganPetition, Long> {}
