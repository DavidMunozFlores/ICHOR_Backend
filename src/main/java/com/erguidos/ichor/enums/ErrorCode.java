package com.erguidos.ichor.enums;

import com.erguidos.ichor.exceptions.ErrorCodeException;

public enum ErrorCode {
    HOSPITAL_NOT_EXISTS(404),
    BLOOD_TYPE_NOT_EXISTS(422),
    USER_NOT_EXISTS(404),
    ALREADY_EXISTS(409),
    BLANK_STRING(400),
    IMPROPER_HEIGHT(400),
    IMPROPER_WEIGHT(400),
    FAILED_DECRYPTION(400),
    UNAUTHORIZED(401);
    
    final int httpStatus;
    
    ErrorCode(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public int getHttpStatus() { return this.httpStatus; }
    
    public ErrorCodeException throwIt() {
        return new ErrorCodeException(this);
    }
}
