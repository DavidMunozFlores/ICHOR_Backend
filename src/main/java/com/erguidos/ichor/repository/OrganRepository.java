package com.erguidos.ichor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erguidos.ichor.entity.Organ;

@Repository
public interface OrganRepository extends JpaRepository<Organ, Long> {
	List<Organ> findByOrganPetitionIsNull();
}

