package com.erguidos.ichor.service;

import java.security.GeneralSecurityException;

public interface KeyServiceInterface {
    String decrypt(String base64CipherText)
            throws GeneralSecurityException;
    
    String getPublicKey();
}
