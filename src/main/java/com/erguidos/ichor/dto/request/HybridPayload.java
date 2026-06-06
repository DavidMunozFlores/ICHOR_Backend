package com.erguidos.ichor.dto.request;

public record HybridPayload(
    String iv,
    String encryptedKey,
    String ciphertext
) {}
