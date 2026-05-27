package com.erguidos.ichor.controller;

import java.security.GeneralSecurityException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erguidos.ichor.dto.request.AuthCredentialsRequest;
import com.erguidos.ichor.dto.request.DecryptRequest;
import com.erguidos.ichor.dto.response.IsUserAuthorizedResponse;
import com.erguidos.ichor.service.auth.AuthServiceInterface;
import com.erguidos.ichor.service.key.KeyServiceInterface;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

	private AuthServiceInterface authService;
	private final KeyServiceInterface keyService;
	
	AuthController(
        AuthServiceInterface authService,
        KeyServiceInterface keyService
    ) {
		this.authService = authService;
		this.keyService = keyService;
	}
	
	@PostMapping("/log-in")
	public ResponseEntity<IsUserAuthorizedResponse> isAuthorized(@RequestBody DecryptRequest dr)
	        throws JsonProcessingException, GeneralSecurityException {
	    AuthCredentialsRequest acr = this.keyService.decryptToObject(dr, AuthCredentialsRequest.class);
		return ResponseEntity.ok(authService.isAuthorized(acr));
	}
}
