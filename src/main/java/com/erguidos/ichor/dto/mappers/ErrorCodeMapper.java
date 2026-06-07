package com.erguidos.ichor.dto.mappers;

import java.time.LocalDateTime;

import com.erguidos.ichor.dto.response.ErrorCodeResponse;

public final class ErrorCodeMapper {
    private ErrorCodeMapper() {
        throw new UnsupportedOperationException("Don't instantiate ErrorCodeMapper");
    }
    
    public static ErrorCodeResponse of(int status, String message) {
        return new ErrorCodeResponse(status, message, LocalDateTime.now());
    }
}
