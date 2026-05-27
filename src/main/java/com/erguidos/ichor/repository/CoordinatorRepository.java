package com.erguidos.ichor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erguidos.ichor.entity.Coordinator;

@Repository
public interface CoordinatorRepository
         extends JpaRepository<Coordinator, Long> {
}
