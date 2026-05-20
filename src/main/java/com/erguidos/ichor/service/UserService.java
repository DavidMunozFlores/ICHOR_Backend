package com.erguidos.ichor.service;

import java.util.List;

import com.erguidos.ichor.dto.response.UserResponse;

public interface UserService {
	List<UserResponse> getAllUsers();
}
