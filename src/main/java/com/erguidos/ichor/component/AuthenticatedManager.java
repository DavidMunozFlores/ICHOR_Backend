package com.erguidos.ichor.component;

import java.security.GeneralSecurityException;
import java.util.function.Function;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.erguidos.ichor.dto.request.AuthenticatedRequest;
import com.erguidos.ichor.dto.request.DataRequestInterface;
import com.erguidos.ichor.dto.request.DecryptRequest;
import com.erguidos.ichor.enums.Role;
import com.erguidos.ichor.exceptions.IncorrectPasswordException;
import com.erguidos.ichor.exceptions.UserNotFoundException;
import com.erguidos.ichor.service.auth.AuthServiceInterface;
import com.erguidos.ichor.service.key.KeyServiceInterface;
import com.fasterxml.jackson.core.JsonProcessingException;

@Component
public class AuthenticatedManager {
    private final KeyServiceInterface keyService;
    private final AuthServiceInterface authService;

    public AuthenticatedManager(
        KeyServiceInterface keyService,
        AuthServiceInterface authService
    ) {
        this.keyService = keyService;
        this.authService = authService;
    }

    public <Data extends DataRequestInterface, Response> ResponseEntity<?> work(
        DecryptRequest decryptRequest,
        Class<Data> dataClass,
        Role role,
        Function<Data, Response> serviceCall,
        Function<Response, ResponseEntity<?>> responseMapper
    ) {
        try {
            AuthenticatedRequest<Data> ar = this.keyService.decryptToAuthenticatedRequest(decryptRequest, dataClass);
            if (!this.authService.authenticate(ar.authCredentials(), role)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            return responseMapper.apply(serviceCall.apply(ar.data()));
        } catch (JsonProcessingException | GeneralSecurityException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        } catch (UserNotFoundException | IncorrectPasswordException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UNKNOWN_USER");
        }
    }
}
