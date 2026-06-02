package com.erguidos.ichor.pre;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

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

@ControllerAdvice
public class DecryptionRequestBodyAdvice extends RequestBodyAdviceAdapter {
    private final KeyServiceInterface keyService;
    private final AuthServiceInterface authService;
    
    public DecryptionRequestBodyAdvice(
        KeyServiceInterface keyService,
        AuthServiceInterface authService
    ) {
        this.keyService = keyService;
        this.authService = authService;
    }
    
    @Override
    public boolean supports(
        MethodParameter methodParameter,
        Type targetType,
        Class<? extends HttpMessageConverter<?>> converterType
    ) {
        return methodParameter.hasParameterAnnotation(AuthenticatedPayload.class);
    }

    @Override
    public HttpInputMessage beforeBodyRead(
        HttpInputMessage inputMessage,
        MethodParameter parameter,
        Type targetType,
        Class<? extends HttpMessageConverter<?>> converterType
    ) throws IOException {
        Class<?> rawClass = parameter.getParameterType();
        
        if (!DataRequestInterface.class.isAssignableFrom(rawClass)) {
            throw new IllegalStateException(
                String.format(
                    "The parameter type [%s] must implement DataRequestInterface",
                    rawClass.getName()
                )
            );
        }
        
        @SuppressWarnings("unchecked")
        Class<? extends DataRequestInterface> targetClass = (Class<? extends DataRequestInterface>) rawClass;
        
        AuthenticatedPayload annotation = parameter.getParameterAnnotation(AuthenticatedPayload.class);
        Role permittedRole = annotation.value();
        
        byte[] encryptedBytes = inputMessage.getBody().readAllBytes();
        DecryptRequest decryptRequest = new ObjectMapper().readValue(encryptedBytes, DecryptRequest.class);
        
        try {
            AuthenticatedRequest<? extends DataRequestInterface> authenticatedRequest = this.keyService.decryptToAuthenticatedRequest(decryptRequest, targetClass);
            
            boolean isAuthenticated = this.authService.authenticate(authenticatedRequest.authCredentials(), permittedRole);
            
            if (!isAuthenticated) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized access");
            }
            
            String decryptedJson = new ObjectMapper().writeValueAsString(authenticatedRequest.data());
            return new HttpInputMessage() {
                @Override
                public InputStream getBody() throws IOException {
                    return new ByteArrayInputStream(decryptedJson.getBytes(StandardCharsets.UTF_8));
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
}
