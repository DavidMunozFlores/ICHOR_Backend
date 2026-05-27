package com.erguidos.ichor.service.key;

import java.security.GeneralSecurityException;

import com.erguidos.ichor.dto.request.AuthenticatedRequest;
import com.erguidos.ichor.dto.request.DataRequestInterface;
import com.erguidos.ichor.dto.request.DecryptRequest;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface KeyServiceInterface {
    String getPublicKey();

    <T> T decryptToObject(DecryptRequest dr, Class<T> targetClass)
            throws GeneralSecurityException, JsonProcessingException;
    
    <T extends DataRequestInterface> AuthenticatedRequest<T> decryptToAuthenticatedRequest(DecryptRequest dr, Class<T> dataClass)
            throws GeneralSecurityException, JsonProcessingException;
}
