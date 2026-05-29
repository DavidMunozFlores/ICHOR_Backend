package com.erguidos.ichor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erguidos.ichor.entity.Coordinator;

@Repository
public interface CoordinatorRepository
         extends JpaRepository<Coordinator, Long> {
    List<Coordinator> findByNameContainingIgnoreCase(String name);
}
