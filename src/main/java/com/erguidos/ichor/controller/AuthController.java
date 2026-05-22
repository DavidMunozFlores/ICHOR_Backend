package com.erguidos.ichor.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erguidos.ichor.dto.request.UserRequestDTO;
import com.erguidos.ichor.dto.response.IsAuthorizedDTO;
import com.erguidos.ichor.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	private AuthService authService;
	
	AuthController(AuthService authService) {
		this.authService = authService;
	}
	
	@PostMapping("/log-in")
	public ResponseEntity<IsAuthorizedDTO> isAuthorized(@RequestBody UserRequestDTO userRequestDTO) {
		return ResponseEntity.ok(authService.isAuthorized(userRequestDTO));
	}
}
