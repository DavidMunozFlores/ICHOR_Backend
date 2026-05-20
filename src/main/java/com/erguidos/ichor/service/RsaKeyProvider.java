package com.erguidos.ichor.service;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RsaKeyProvider {
    private static final String CIPHER_ALGORITHM = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";
    private static final String KEY_ALGORITHM = "RSA";
    
    private static final String TEST_MESSAGE = "ichor-key-check";
    
    private final PrivateKey privateKey;
    private final PublicKey publicKey;
    
    public RsaKeyProvider(
        @Value("${app.security.key.private}") String privateKeyBase64,
        @Value("${app.security.key.public}") String publicKeyBase64
    ) throws IllegalStateException {
        this.privateKey = RsaKeyProvider.decodePrivateKey(privateKeyBase64);
        this.publicKey = RsaKeyProvider.decodePublicKey(publicKeyBase64);
        this.verifyKeyPair();
    }
    
    private void verifyKeyPair() throws IllegalStateException {
        try {
            Cipher encryptCipher = Cipher.getInstance(CIPHER_ALGORITHM);
            encryptCipher.init(Cipher.ENCRYPT_MODE, this.publicKey);

            byte[] encrypted = encryptCipher.doFinal(
                TEST_MESSAGE.getBytes(StandardCharsets.UTF_8)
            );

            Cipher decryptCipher = Cipher.getInstance(CIPHER_ALGORITHM);
            decryptCipher.init(Cipher.DECRYPT_MODE, this.privateKey);

            byte[] decrypted = decryptCipher.doFinal(encrypted);

            String result = new String(decrypted, StandardCharsets.UTF_8);

            if (!TEST_MESSAGE.equals(result)) {
                throw new IllegalStateException(
                    "RSA public/private keys do not match"
                );
            }
        } catch (NoSuchAlgorithmException |
                 NoSuchPaddingException |
                 InvalidKeyException |
                 IllegalBlockSizeException |
                 BadPaddingException e) {
            throw new IllegalStateException(
                "Failed to initialize RSA key provider",
                e
            );
        }
    }
    
    private static byte[] decodeKey(String key) {
        String normalized = key.replaceAll("\\s", "");
        return java.util.Base64.getDecoder().decode(normalized);
    }
    
    private static PrivateKey decodePrivateKey(String privateKeyBase64)
            throws IllegalStateException {
        byte[] privateKeyBytes = RsaKeyProvider.decodeKey(privateKeyBase64);
        try {
            return KeyFactory.getInstance(RsaKeyProvider.KEY_ALGORITHM)
                             .generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
        } catch (InvalidKeySpecException |
                 NoSuchAlgorithmException e) {
            throw new IllegalStateException(
                "Failed to initialize RSA key provider",
                e
            );
        }
    }
    
    private static PublicKey decodePublicKey(String publicKeyBase64)
            throws IllegalStateException {
        byte[] publicKeyBytes = RsaKeyProvider.decodeKey(publicKeyBase64);
        try {
            return KeyFactory.getInstance(RsaKeyProvider.KEY_ALGORITHM)
                             .generatePublic(new X509EncodedKeySpec(publicKeyBytes));
        } catch (InvalidKeySpecException |
                 NoSuchAlgorithmException e) {
            throw new IllegalStateException(
                "Failed to initialize RSA key provider",
                e
            );
        }
    }

    public PrivateKey getPrivateKey() { return this.privateKey; }
    public PublicKey getPublicKey() { return this.publicKey; }
}
