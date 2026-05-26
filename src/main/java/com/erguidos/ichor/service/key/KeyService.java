package com.erguidos.ichor.service.key;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.spec.MGF1ParameterSpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.erguidos.ichor.dto.request.AuthenticatedRequest;
import com.erguidos.ichor.dto.request.DataRequestInterface;
import com.erguidos.ichor.dto.request.DecryptRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KeyService implements KeyServiceInterface {
    private static final String CIPHER_ALGORITHM = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";

    private final RsaKeyProvider rsaKeyProvider;
    
    private final String stage;
    
    public KeyService(RsaKeyProvider rsaKeyProvider, @Value("${app.development.stage}") String stage) {
        this.rsaKeyProvider = rsaKeyProvider;
        this.stage = stage;
    }
    
    private String decrypt(String base64CipherText) throws GeneralSecurityException {
        byte[] cipherBytes = Base64.getDecoder().decode(base64CipherText);
        Cipher cipher = Cipher.getInstance(KeyService.CIPHER_ALGORITHM);
        if ("BACK".equals(this.stage)) {
            cipher.init(Cipher.DECRYPT_MODE, this.rsaKeyProvider.getPrivateKey());
        } else {
            OAEPParameterSpec oaepParams = new OAEPParameterSpec(
                "SHA-256",
                "MGF1",
                MGF1ParameterSpec.SHA256,
                PSource.PSpecified.DEFAULT
            );
            cipher.init(Cipher.DECRYPT_MODE, this.rsaKeyProvider.getPrivateKey(), oaepParams);
        }
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
    
    @Override
    public <T> T decryptToObject(DecryptRequest dr, Class<T> targetClass)
            throws GeneralSecurityException, JsonProcessingException {
        return new ObjectMapper()
                .readValue(
                    this.decrypt(dr.data()),
                    targetClass
                );
    }

    @Override
    public <T extends DataRequestInterface> AuthenticatedRequest<T> decryptToAuthenticatedRequest(
        DecryptRequest dr,
        Class<T> dataClass
    ) throws GeneralSecurityException, JsonProcessingException {
        JavaType jt = new ObjectMapper()
                             .getTypeFactory()
                             .constructParametricType(
                                     AuthenticatedRequest.class,
                                     dataClass
                             );
        return new ObjectMapper().readValue(
            this.decrypt(dr.data()),
            jt
        );
    }
}
