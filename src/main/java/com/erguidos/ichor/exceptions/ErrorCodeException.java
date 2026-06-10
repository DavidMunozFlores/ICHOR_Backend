package com.erguidos.ichor.exceptions;

import com.erguidos.ichor.error.ErrorDetails;

public class ErrorCodeException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    private final ErrorDetails errorDetails;
    
    public ErrorCodeException(ErrorDetails errorDetails) {
        super(errorDetails.name());
        this.errorDetails = errorDetails;
    }
    
    public int getHttpStatus() { return this.errorDetails.getHttpStatus(); }
    
    public ErrorDetails getErrorDetails() { return this.errorDetails; }
}
