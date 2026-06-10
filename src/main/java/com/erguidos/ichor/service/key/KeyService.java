package com.erguidos.ichor.service.key;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.spec.MGF1ParameterSpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import com.erguidos.ichor.dto.request.AuthenticatedRequest;
import com.erguidos.ichor.dto.request.DataRequestInterface;
import com.erguidos.ichor.dto.request.DecryptRequest;
import com.erguidos.ichor.dto.request.HybridPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KeyService implements KeyServiceInterface {
    private static final String RSA_CIPHER_ALGORITHM = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";
    private static final String AES_CIPHER_ALGORITHM = "AES/GCM/NoPadding";
    private static final int GCM_TAG_LENGTH_BITS = 128;
    
    private final RsaKeyProvider rsaKeyProvider;
    private final ObjectMapper objectMapper;
    
    public KeyService(
        RsaKeyProvider rsaKeyProvider,
        ObjectMapper objectMapper
    ) {
        this.rsaKeyProvider = rsaKeyProvider;
        this.objectMapper = objectMapper;
    }
    
    private String decrypt(String base64WrappedPayload) throws GeneralSecurityException, JsonMappingException, JsonProcessingException {
        String decodedJsonWrapper = new String(
                Base64.getDecoder().decode(base64WrappedPayload),
                StandardCharsets.UTF_8
        );
        HybridPayload payload = this.objectMapper.readValue(decodedJsonWrapper, HybridPayload.class);
        
        byte[] ivBytes = Base64.getDecoder().decode(payload.iv());
        byte[] encryptedAesKeyBytes = Base64.getDecoder().decode(payload.encryptedKey());
        byte[] cipherBytes = Base64.getDecoder().decode(payload.ciphertext());
        
        Cipher rsaCipher = Cipher.getInstance(KeyService.RSA_CIPHER_ALGORITHM);
        OAEPParameterSpec oaepParams = new OAEPParameterSpec(
            "SHA-256",
            "MGF1",
            MGF1ParameterSpec.SHA256,
            PSource.PSpecified.DEFAULT
        );
        rsaCipher.init(Cipher.DECRYPT_MODE, this.rsaKeyProvider.getPrivateKey(), oaepParams);
        byte[] rawAesKeyBytes = rsaCipher.doFinal(encryptedAesKeyBytes);
        
        SecretKeySpec aesKeySpec = new SecretKeySpec(rawAesKeyBytes, "AES");
        Cipher aesCipher = Cipher.getInstance(KeyService.AES_CIPHER_ALGORITHM);
        GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_LENGTH_BITS, ivBytes);
        
        aesCipher.init(Cipher.DECRYPT_MODE, aesKeySpec, gcmSpec);
        byte[] textBytes = aesCipher.doFinal(cipherBytes);
        
        return new String(textBytes, StandardCharsets.UTF_8);
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
        return this.objectMapper
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
        JavaType jt = this.objectMapper
                             .getTypeFactory()
                             .constructParametricType(
                                     AuthenticatedRequest.class,
                                     dataClass
                             );
        return this.objectMapper
                .readValue(
                    this.decrypt(dr.data()),
                    jt
                );
    }
}
