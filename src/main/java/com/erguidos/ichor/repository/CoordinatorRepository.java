package com.erguidos.ichor.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erguidos.ichor.entity.Coordinator;

public interface CoordinatorRepository extends JpaRepository<Coordinator, Long> {
    Optional<Coordinator> getByUserId(Long userId);
}
