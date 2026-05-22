package com.erguidos.ichor.dto.response;

import com.erguidos.ichor.entity.User;

public record UserResponseDTO(Long id, String username) {
	public static UserResponseDTO from(User user) {
		return new UserResponseDTO(user.getId(), user.getUsername());
	}
}
