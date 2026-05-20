package com.erguidos.ichor.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.erguidos.ichor.dto.response.UserResponse;
import com.erguidos.ichor.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	private UserRepository userRepository;
	
	UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public List<UserResponse> getAllUsers() {
		return userRepository
				.findAll()
				.stream()
				.map(UserResponse::from)
				.toList();
	}
}
