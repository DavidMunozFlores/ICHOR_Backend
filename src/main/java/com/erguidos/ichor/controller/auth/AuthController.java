package com.erguidos.ichor.controller.auth;

import com.erguidos.ichor.service.auth.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erguidos.ichor.annotations.UnauthenticatedPayload;
import com.erguidos.ichor.dto.request.AuthCredentialsRequest;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = {"${app.cors.allowed}"})
public class AuthController {
    private final AuthService authService;
	
	AuthController(
        AuthService authService
    ) {
        this.authService = authService;
	}
	
	@PostMapping("/log-in")
	public ResponseEntity<?> isAuthorized(
        @RequestBody AuthCredentialsRequest acr
    ) {
	    return ResponseEntity.ok(this.authService.getRole(acr));
	}
}
