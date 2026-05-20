package com.erguidos.ichor.service;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Base64;

import javax.crypto.Cipher;

import org.springframework.stereotype.Service;

@Service
public class KeyService {
    private static final String CIPHER_ALGORITHM = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";

    private final RsaKeyProvider rsaKeyProvider;
    
    public KeyService(RsaKeyProvider rsaKeyProvider) {
        this.rsaKeyProvider = rsaKeyProvider;
    }
    
    public String decrypt(String base64CipherText) throws GeneralSecurityException {
        byte[] cipherBytes = Base64.getDecoder().decode(base64CipherText);
        Cipher cipher = Cipher.getInstance(KeyService.CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, this.rsaKeyProvider.getPrivateKey());
        return new String(cipher.doFinal(cipherBytes), StandardCharsets.UTF_8);
    }
    
    public String getPublicKey() {
        return Base64.getEncoder()
                     .encodeToString(
                         this.rsaKeyProvider
                             .getPublicKey()
                             .getEncoded()
                     );
    }
}
