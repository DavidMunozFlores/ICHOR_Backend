package com.erguidos.ichor.service.key;

import java.security.GeneralSecurityException;

import com.erguidos.ichor.dto.request.DecryptRequest;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface KeyServiceInterface {
    String getPublicKey();

    <T> T decryptToObject(DecryptRequest dr, Class<T> targetClass)
            throws GeneralSecurityException, JsonProcessingException;
}
