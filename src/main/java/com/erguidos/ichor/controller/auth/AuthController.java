package com.erguidos.ichor.controller.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erguidos.ichor.dto.request.DecryptRequest;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = {"${app.cors.allowed}"})
public class AuthController {
    private final AuthSemiController semi;
	
	AuthController(
        AuthSemiController semi
    ) {
	    this.semi = semi;
	}
	
	@PostMapping("/log-in")
	public ResponseEntity<?> isAuthorized(@RequestBody DecryptRequest dr) {
	    return this.semi.isAuthorized(dr);
	}
}
