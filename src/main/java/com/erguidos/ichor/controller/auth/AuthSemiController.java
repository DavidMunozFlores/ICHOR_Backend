package com.erguidos.ichor.controller.auth;

import java.security.GeneralSecurityException;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.erguidos.ichor.controller.AuthenticatedSemiController;
import com.erguidos.ichor.dto.request.AuthCredentialsRequest;
import com.erguidos.ichor.dto.request.DecryptRequest;
import com.erguidos.ichor.service.auth.AuthServiceInterface;
import com.erguidos.ichor.service.key.KeyServiceInterface;
import com.fasterxml.jackson.core.JsonProcessingException;

@Component
public class AuthSemiController extends AuthenticatedSemiController {
    AuthSemiController(
        KeyServiceInterface keyService,
        AuthServiceInterface authService
    ) {
        super(keyService, authService);
    }
    
    public ResponseEntity<?> isAuthorized(DecryptRequest dr) {
        try {
            AuthCredentialsRequest acr = this.keyService.decryptToObject(dr, AuthCredentialsRequest.class);
            return ResponseEntity.ok(authService.isAuthorized(acr));
        } catch (JsonProcessingException | GeneralSecurityException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
