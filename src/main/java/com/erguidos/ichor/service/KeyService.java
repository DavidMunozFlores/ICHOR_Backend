package com.erguidos.ichor.service;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.spec.MGF1ParameterSpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;

import org.springframework.stereotype.Service;

@Service
public class KeyService implements KeyServiceInterface {
    private static final String CIPHER_ALGORITHM = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";

    private final RsaKeyProvider rsaKeyProvider;
    
    public KeyService(RsaKeyProvider rsaKeyProvider) {
        this.rsaKeyProvider = rsaKeyProvider;
    }
    
    @Override
    public String decrypt(String base64CipherText) throws GeneralSecurityException {
        byte[] cipherBytes = Base64.getDecoder().decode(base64CipherText);
        Cipher cipher = Cipher.getInstance(KeyService.CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, this.rsaKeyProvider.getPrivateKey());
        return new String(cipher.doFinal(cipherBytes), StandardCharsets.UTF_8);
    }
    
    @Override
    public String getPublicKey() {
        return Base64.getEncoder()
                     .encodeToString(
                         this.rsaKeyProvider
                             .getPublicKey()
                             .getEncoded()
                     );
    }
}
