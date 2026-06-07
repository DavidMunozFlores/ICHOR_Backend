package com.erguidos.ichor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.erguidos.ichor.entity.User;

public interface WorkerRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE TYPE(u) IN (Coordinator, Doctor)")
    List<User> findCoordinatorsAndDoctors();
}
