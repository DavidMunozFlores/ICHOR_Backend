package com.erguidos.ichor.dto.response;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;

public record ErrorCodeResponse(int status, String message, LocalDateTime time) {
    public static ErrorCodeResponse of(int status, String message) {
        return new ErrorCodeResponse(status, message, LocalDateTime.now());
    }
    
    public ResponseEntity<ErrorCodeResponse> getResponse() {
        return ResponseEntity.status(this.status).body(this);
    }
}
