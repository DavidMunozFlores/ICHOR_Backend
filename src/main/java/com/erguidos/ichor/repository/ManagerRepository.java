package com.erguidos.ichor.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erguidos.ichor.entity.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
	Optional<Manager> findManagerByUsername(String username);
}
