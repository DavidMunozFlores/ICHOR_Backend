package com.erguidos.ichor.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erguidos.ichor.dto.request.LoginUserRequestDTO;
import com.erguidos.ichor.dto.response.IsUserAuthorizedDTO;
import com.erguidos.ichor.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	private AuthService authService;
	
	AuthController(AuthService authService) {
		this.authService = authService;
	}
	
	@PostMapping("/log-in")
	public ResponseEntity<IsUserAuthorizedDTO> isAuthorized(@RequestBody LoginUserRequestDTO userRequestDTO) {
		return ResponseEntity.ok(authService.isAuthorized(userRequestDTO));
	}
}
