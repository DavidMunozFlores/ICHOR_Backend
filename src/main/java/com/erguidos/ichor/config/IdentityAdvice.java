package com.erguidos.ichor.config;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import com.erguidos.ichor.annotations.AuthenticatedIdentity;
import com.erguidos.ichor.dto.request.AuthCredentialsRequest;
import com.erguidos.ichor.dto.request.DecryptRequest;
import com.erguidos.ichor.dto.request.EntityId;
import com.erguidos.ichor.dto.request.UserIdentity;
import com.erguidos.ichor.enums.Role;
import com.erguidos.ichor.error.Errors;
import com.erguidos.ichor.exceptions.IncorrectPasswordException;
import com.erguidos.ichor.exceptions.UserNotFoundException;
import com.erguidos.ichor.service.auth.AuthServiceInterface;
import com.erguidos.ichor.service.key.KeyServiceInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@ControllerAdvice
public class IdentityAdvice extends RequestBodyAdviceAdapter {
    private final KeyServiceInterface keyService;
    private final AuthServiceInterface authService;
    private final ObjectMapper objectMapper;
    
    public IdentityAdvice(
            KeyServiceInterface keyService, 
            AuthServiceInterface authService,
            ObjectMapper objectMapper
    ) {
        this.keyService = keyService;
        this.authService = authService;
        this.objectMapper = objectMapper;
    }
    
    @Override
    public boolean supports(
        MethodParameter methodParameter,
        Type targetType,
        Class<? extends HttpMessageConverter<?>> converterType
    ) {
        return methodParameter.hasMethodAnnotation(AuthenticatedIdentity.class);
    }
    
    @Override
    public HttpInputMessage beforeBodyRead(
        HttpInputMessage inputMessage,
        MethodParameter parameter,
        Type targetType,
        Class<? extends HttpMessageConverter<?>> converterType
    ) throws IOException {
        byte[] encryptedBytes = inputMessage.getBody().readAllBytes();
        DecryptRequest decryptRequest = objectMapper.readValue(encryptedBytes, DecryptRequest.class);

        Role roleAllowed = parameter.getParameterAnnotation(AuthenticatedIdentity.class).value();

        try {
            AuthCredentialsRequest credentials = this.keyService.decryptToObject(decryptRequest, AuthCredentialsRequest.class);
            UserIdentity userIdentity = this.authService.identify(credentials);

            if (roleAllowed != userIdentity.role()) { throw Errors.Auth.UNAUTHORIZED.asException(); }
            EntityId identity = new EntityId(userIdentity.id());
            String forwardJson = this.objectMapper.writeValueAsString(identity);
            
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
            throw Errors.User.NOT_EXISTS.asException();
        } catch (JsonProcessingException | GeneralSecurityException e) {
            throw Errors.Auth.FAILED_DECRYPTION.asException();
        }
    }
}
