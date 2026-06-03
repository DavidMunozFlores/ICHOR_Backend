package com.erguidos.ichor.config;

import com.erguidos.ichor.annotations.AuthenticatedOnly;
import com.erguidos.ichor.dto.request.AuthenticatedRequest;
import com.erguidos.ichor.dto.request.DataRequestInterface;
import com.erguidos.ichor.dto.request.EmptyDataRequest;
import com.erguidos.ichor.enums.Role;
import com.erguidos.ichor.service.auth.AuthServiceInterface;
import com.erguidos.ichor.service.key.KeyServiceInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import java.lang.reflect.Type;
import java.util.Optional;

@ControllerAdvice
public class AuthOnlyDecryptionAdvice extends BaseDecryptionAdvice {

    public AuthOnlyDecryptionAdvice(
        KeyServiceInterface keyService,
        AuthServiceInterface authService,
        ObjectMapper objectMapper
    ) {
        super(keyService, authService, objectMapper);
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return methodParameter.hasParameterAnnotation(AuthenticatedOnly.class);
    }

    @Override
    protected Optional<Role> getPermittedRole(MethodParameter parameter) {
        return Optional.of(parameter.getParameterAnnotation(AuthenticatedOnly.class).value());
    }

    @Override
    protected Class<? extends DataRequestInterface> getDataClass(MethodParameter parameter) {
        return EmptyDataRequest.class;
    }

    @Override
    protected String determineForwardJson(AuthenticatedRequest<? extends DataRequestInterface> authRequest, MethodParameter parameter) throws JsonProcessingException {
        return objectMapper.writeValueAsString(authRequest.authCredentials());
    }
}