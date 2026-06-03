package com.erguidos.ichor.config;

import com.erguidos.ichor.dto.request.AuthenticatedRequest;
import com.erguidos.ichor.dto.request.DataRequestInterface;
import com.erguidos.ichor.dto.request.DecryptRequest;
import com.erguidos.ichor.enums.Role;
import com.erguidos.ichor.exceptions.IncorrectPasswordException;
import com.erguidos.ichor.exceptions.UserNotFoundException;
import com.erguidos.ichor.service.auth.AuthServiceInterface;
import com.erguidos.ichor.service.key.KeyServiceInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Optional;

public abstract class BaseDecryptionAdvice extends RequestBodyAdviceAdapter {

    protected final KeyServiceInterface keyService;
    protected final AuthServiceInterface authService;
    protected final ObjectMapper objectMapper;

    protected BaseDecryptionAdvice(
        KeyServiceInterface keyService,
        AuthServiceInterface authService
    ) {
        this.keyService = keyService;
        this.authService = authService;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public HttpInputMessage beforeBodyRead(
        HttpInputMessage inputMessage,
        MethodParameter parameter,
        Type targetType,
        Class<? extends HttpMessageConverter<?>> converterType
    ) throws IOException {
        byte[] encryptedBytes = inputMessage.getBody().readAllBytes();
        DecryptRequest decryptRequest = this.objectMapper.readValue(encryptedBytes, DecryptRequest.class);
        
        Optional<Role> permittedRole = this.getPermittedRole(parameter);
        Class<? extends DataRequestInterface> dataClass = this.getDataClass(parameter);
        
        try {
            AuthenticatedRequest<? extends DataRequestInterface> authenticatedRequest = this.keyService.decryptToAuthenticatedRequest(decryptRequest, dataClass);

            if (permittedRole.isPresent()) {
                boolean isAuthenticated = this.authService.authenticate(authenticatedRequest.authCredentials(), permittedRole.get());
                
                if (!isAuthenticated) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized access");
                }
            }
            
            String forwardJson = this.determineForwardJson(authenticatedRequest, parameter);
            
            return new HttpInputMessage() {
                @Override
                public InputStream getBody() throws IOException {
                    return new ByteArrayInputStream(forwardJson.getBytes(StandardCharsets.UTF_8));
                }
                
                @Override
                public HttpHeaders getHeaders() {
                    return inputMessage.getHeaders();
                }
            };
        } catch (UserNotFoundException | IncorrectPasswordException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "UNKNOWN_USER");
        } catch (JsonProcessingException | GeneralSecurityException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Decryption failed", e);
        }
    }

    protected abstract Optional<Role> getPermittedRole(MethodParameter parameter);
    protected abstract Class<? extends DataRequestInterface> getDataClass(MethodParameter parameter);
    protected abstract String determineForwardJson(
        AuthenticatedRequest<? extends DataRequestInterface> authRequest,
        MethodParameter parameter
    ) throws JsonProcessingException;
}