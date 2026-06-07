package com.erguidos.ichor.dto.response;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;

public record ErrorCodeResponse(int status, String message, LocalDateTime time) {
    public ResponseEntity<ErrorCodeResponse> toResponse() {
        return ResponseEntity.status(this.status).body(this);
    }
}
