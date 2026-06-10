package com.erguidos.ichor.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erguidos.ichor.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findUserByUsername(String username);
}
