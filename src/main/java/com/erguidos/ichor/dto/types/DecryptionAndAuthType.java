package com.erguidos.ichor.dto.types;

import org.springframework.http.ResponseEntity;

import com.erguidos.ichor.dto.request.DataRequestInterface;

public sealed interface DecryptionAndAuthType<D extends DataRequestInterface>
                permits DecryptionAndAuthType.FailDecryption,
                        DecryptionAndAuthType.FailAuthentication,
                        DecryptionAndAuthType.Success {
    public record FailDecryption<D extends DataRequestInterface>(ResponseEntity<?> response) implements DecryptionAndAuthType<D> {}
    public record FailAuthentication<D extends DataRequestInterface>(ResponseEntity<?> response) implements DecryptionAndAuthType<D> {}
    public record Success<D extends DataRequestInterface>(D data) implements DecryptionAndAuthType<D> {}
}
