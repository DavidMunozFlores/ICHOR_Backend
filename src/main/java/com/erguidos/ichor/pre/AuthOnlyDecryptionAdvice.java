package com.erguidos.ichor.pre;

import com.erguidos.ichor.dto.request.AuthenticatedRequest;
import com.erguidos.ichor.dto.request.DataRequestInterface;
import com.erguidos.ichor.dto.request.EmptyDataRequest;
import com.erguidos.ichor.enums.Role;
import com.erguidos.ichor.service.auth.AuthServiceInterface;
import com.erguidos.ichor.service.key.KeyServiceInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import java.lang.reflect.Type;

@ControllerAdvice
public class AuthOnlyDecryptionAdvice extends BaseDecryptionAdvice {

    public AuthOnlyDecryptionAdvice(
        KeyServiceInterface keyService,
        AuthServiceInterface authService
    ) {
        super(keyService, authService);
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return methodParameter.hasParameterAnnotation(AuthenticatedOnly.class);
    }

    @Override
    protected Role getPermittedRole(MethodParameter parameter) {
        return parameter.getParameterAnnotation(AuthenticatedOnly.class).value();
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