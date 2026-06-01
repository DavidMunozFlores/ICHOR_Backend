package com.erguidos.ichor.controller;

import java.security.GeneralSecurityException;
import java.util.function.Function;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.erguidos.ichor.dto.request.AuthenticatedRequest;
import com.erguidos.ichor.dto.request.DataRequestInterface;
import com.erguidos.ichor.dto.request.DecryptRequest;
import com.erguidos.ichor.dto.types.DecryptionAndAuthType;
import com.erguidos.ichor.enums.Role;
import com.erguidos.ichor.exceptions.IncorrectPasswordException;
import com.erguidos.ichor.exceptions.UserNotFoundException;
import com.erguidos.ichor.service.auth.AuthServiceInterface;
import com.erguidos.ichor.service.key.KeyServiceInterface;
import com.fasterxml.jackson.core.JsonProcessingException;

public abstract class AuthenticatedSemiController {
    private final KeyServiceInterface keyService;
    private final AuthServiceInterface authService;
    
    protected AuthenticatedSemiController(
        KeyServiceInterface keyService,
        AuthServiceInterface authService
    ) {
        this.keyService = keyService;
        this.authService = authService;
    }
    
    protected <D extends DataRequestInterface> DecryptionAndAuthType<D> decryptAndAuthenticate(
        DecryptRequest dr,
        Class<D> dataClass,
        Role permittedRole
    ) {
        try {
            AuthenticatedRequest<D> ar = this.keyService.decryptToAuthenticatedRequest(dr, dataClass);
            boolean isAuthenticated = this.authService.authenticate(ar.authCredentials(), permittedRole);
            
            if (! isAuthenticated) {
                return new DecryptionAndAuthType.FailAuthentication<D>(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
            }
            
            return new DecryptionAndAuthType.Success<D>(ar.data());
        } catch (JsonProcessingException | GeneralSecurityException e) {
            return new DecryptionAndAuthType.FailDecryption<D>(ResponseEntity.internalServerError().build());
        } catch (UserNotFoundException | IncorrectPasswordException e) {
            return new DecryptionAndAuthType.FailAuthentication<D>(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UNKNOWN_USER"));
        }
    }
    
    protected <D extends DataRequestInterface> ResponseEntity<?> process(
        DecryptRequest dr,
        Class<D> dataClass,
        Role permittedRole,
        Function<D, ResponseEntity<?>> serviceCall
    ) {
        DecryptionAndAuthType<D> daat = this.decryptAndAuthenticate(dr, dataClass, permittedRole);
        
        return switch (daat) {
            case DecryptionAndAuthType.FailDecryption(ResponseEntity<?> response) -> response;
            case DecryptionAndAuthType.FailAuthentication(ResponseEntity<?> response) -> response;
            case DecryptionAndAuthType.Success(D data) -> serviceCall.apply(data);
        };
    }
}
