package com.erguidos.ichor.config;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.erguidos.ichor.annotations.AuthenticatedRequestPayload;
import com.erguidos.ichor.dto.request.AuthenticatedRequest;
import com.erguidos.ichor.dto.request.DataRequestInterface;
import com.erguidos.ichor.enums.Role;
import com.erguidos.ichor.service.auth.AuthServiceInterface;
import com.erguidos.ichor.service.key.KeyServiceInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice
public class RequestPayloadDecryptionAdvice extends BaseDecryptionAdvice {
    public RequestPayloadDecryptionAdvice(
        KeyServiceInterface keyService,
        AuthServiceInterface authService,
        ObjectMapper objectMapper
    ) {
        super(keyService, authService, objectMapper);
    }

    @Override
    public boolean supports(
        MethodParameter methodParameter,
        Type targetType,
        Class<? extends HttpMessageConverter<?>> converterType
    ) {
        return methodParameter.hasParameterAnnotation(AuthenticatedRequestPayload.class);
    }

    @Override
    protected Optional<Role> getPermittedRole(MethodParameter parameter) {
        return Optional.of(parameter.getParameterAnnotation(AuthenticatedRequestPayload.class).value());
    }

    @Override
    protected Class<? extends DataRequestInterface> getDataClass(MethodParameter parameter) {
        Type genericParameterType = parameter.getGenericParameterType();

        if (genericParameterType instanceof ParameterizedType parameterizedType) {
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            
            if (actualTypeArguments.length > 0 && actualTypeArguments[0] instanceof Class<?>) {
                Class<?> rawDataClass = (Class<?>) actualTypeArguments[0];

                if (!DataRequestInterface.class.isAssignableFrom(rawDataClass)) {
                    throw new IllegalStateException(
                        String.format("The generic type [%s] must implement DataRequestInterface", rawDataClass.getName())
                    );
                }

                @SuppressWarnings("unchecked")
                Class<? extends DataRequestInterface> targetDataClass = (Class<? extends DataRequestInterface>) rawDataClass;
                return targetDataClass;
            }
        }

        throw new IllegalStateException("The parameter decorated with @AuthenticatedRequestPayload must use explicit generics (e.g., AuthenticatedRequest<YourDTO>).");
    }

    @Override
    protected String determineForwardJson(
        AuthenticatedRequest<? extends DataRequestInterface> authRequest,
        MethodParameter parameter
    ) throws JsonProcessingException {
        return this.objectMapper.writeValueAsString(authRequest);
    }
}
