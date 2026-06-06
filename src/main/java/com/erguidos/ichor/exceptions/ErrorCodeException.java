package com.erguidos.ichor.exceptions;

import com.erguidos.ichor.enums.ErrorCode;

public class ErrorCodeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private final int httpStatus;
    
    public ErrorCodeException(ErrorCode errorCode) {
        super(errorCode.toString());
        this.httpStatus = errorCode.getHttpStatus();
    }
    
    public int getHttpStatus() { return this.httpStatus; }
}
