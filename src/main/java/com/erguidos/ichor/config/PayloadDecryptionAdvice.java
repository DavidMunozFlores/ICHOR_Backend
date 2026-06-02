package com.erguidos.ichor.config;

import java.lang.reflect.Type;
import java.util.Optional;

import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.erguidos.ichor.annotations.AuthenticatedPayload;
import com.erguidos.ichor.dto.request.AuthenticatedRequest;
import com.erguidos.ichor.dto.request.DataRequestInterface;
import com.erguidos.ichor.enums.Role;
import com.erguidos.ichor.service.auth.AuthServiceInterface;
import com.erguidos.ichor.service.key.KeyServiceInterface;
import com.fasterxml.jackson.core.JsonProcessingException;

@ControllerAdvice
public class PayloadDecryptionAdvice extends BaseDecryptionAdvice {
    public PayloadDecryptionAdvice(
        KeyServiceInterface keyService,
        AuthServiceInterface authService
    ) {
        super(keyService, authService);
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
    protected Optional<Role> getPermittedRole(MethodParameter parameter) {
        return Optional.of(parameter.getParameterAnnotation(AuthenticatedPayload.class).value());
    }

    @Override
    protected Class<? extends DataRequestInterface> getDataClass(MethodParameter parameter) {
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
        return targetClass;
    }

    @Override
    protected String determineForwardJson(AuthenticatedRequest<? extends DataRequestInterface> authRequest,
            MethodParameter parameter) throws JsonProcessingException {
        return this.objectMapper.writeValueAsString(authRequest.data());
    }
}
