package com.erguidos.ichor.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erguidos.ichor.dto.request.AuthCredentialsRequest;
import com.erguidos.ichor.dto.response.IsUserAuthorizedResponse;
import com.erguidos.ichor.service.auth.AuthServiceInterface;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

	private AuthServiceInterface authService;
	
	AuthController(AuthServiceInterface authService) {
		this.authService = authService;
	}
	
	@PostMapping("/log-in")
	public ResponseEntity<IsUserAuthorizedResponse> isAuthorized(@RequestBody AuthCredentialsRequest userRequestDTO) {
		return ResponseEntity.ok(authService.isAuthorized(userRequestDTO));
	}
}
