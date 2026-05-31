package com.erguidos.ichor.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erguidos.ichor.entity.Coordinator;

@Repository
public interface CoordinatorRepository
         extends JpaRepository<Coordinator, Long> {
	
	  Optional<Coordinator> findByUsername(String username);
    List<Coordinator> findByUsernameContainingIgnoreCase(String username);
}
