package com.erguidos.ichor.config;

import com.erguidos.ichor.annotations.UnauthenticatedPayload;
import com.erguidos.ichor.dto.request.AuthCredentialsRequest;
import com.erguidos.ichor.dto.request.DecryptRequest;
import com.erguidos.ichor.error.Errors;
import com.erguidos.ichor.service.key.KeyServiceInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;

@ControllerAdvice
public class LoginDecryptionAdvice extends RequestBodyAdviceAdapter {
    private final KeyServiceInterface keyService;
    private final ObjectMapper objectMapper;
    
    public LoginDecryptionAdvice(
        KeyServiceInterface keyService,
        ObjectMapper objectMapper
    ) {
        this.keyService = keyService;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return methodParameter.hasParameterAnnotation(UnauthenticatedPayload.class);
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
        
        try {
            AuthCredentialsRequest acr = this.keyService.decryptToObject(decryptRequest, AuthCredentialsRequest.class);
            String forwardJson = this.objectMapper.writeValueAsString(acr);
            
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
        } catch (JsonProcessingException | GeneralSecurityException e) {
            throw Errors.Auth.FAILED_DECRYPTION.asException();
        }
    }
}