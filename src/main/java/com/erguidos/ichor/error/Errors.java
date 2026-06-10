package com.erguidos.ichor.error;

public final class Errors {
    private Errors() {
        throw new UnsupportedOperationException("Don't instantiate Error");
    }
    
    public enum Patient implements ErrorDetails {
        NOT_EXISTS(404),
        BLANK_STRING(400),
        IMPROPER_HEIGHT(400),
        IMPROPER_WEIGHT(400),
        NULL_TEXT(400),
        NULL_BLOOD_TYPE(400),
        ALREADY_EXISTS(409),
        INVALID_NAME(400);

        private final int httpStatus;
        Patient(int httpStatus) { this.httpStatus = httpStatus; }
        @Override public int getHttpStatus() { return this.httpStatus; }
    }
    
    public enum BloodType implements ErrorDetails {
        NOT_EXISTS(422);

        private final int httpStatus;
        BloodType(int httpStatus) { this.httpStatus = httpStatus; }
        @Override public int getHttpStatus() { return this.httpStatus; }
    }

    public enum Hospital implements ErrorDetails {
        NOT_EXISTS(404),
        NULL_NAME(400),
        BLANK_NAME(400),
        INVALID_NAME(400),
        NULL_ADDRESS(400),
        BLANK_ADDRESS(400),
        INVALID_ADDRESS(400),
        NULL_LONGITUDE(400),
        NULL_LATITUDE(400),
        OUT_OF_BOUNDS_LATITUDE(400);

        private final int httpStatus;
        Hospital(int httpStatus) { this.httpStatus = httpStatus; }
        @Override public int getHttpStatus() { return this.httpStatus; }
    }

    public enum Auth implements ErrorDetails {
        FAILED_DECRYPTION(400),
        UNAUTHORIZED(401);

        private final int httpStatus;
        Auth(int httpStatus) { this.httpStatus = httpStatus; }
        @Override public int getHttpStatus() { return this.httpStatus; }
    }
    
    public enum User implements ErrorDetails {
        NOT_EXISTS(404),
        WRONG_ROLE(400),
        ALREADY_EXISTS(409),
        NULL_USERNAME(400),
        BLANK_USERNAME(400),
        INVALID_USERNAME(400),
        NULL_PASSWORD(400),
        BLANK_PASSWORD(400),
        INVALID_PASSWORD(400);

        private final int httpStatus;
        User(int httpStatus) { this.httpStatus = httpStatus; }
        @Override public int getHttpStatus() { return this.httpStatus; }
    }
    
    public enum Coordinator implements ErrorDetails {
        NULL_HOSPITAL(400);

        private final int httpStatus;
        Coordinator(int httpStatus) { this.httpStatus = httpStatus; }
        @Override public int getHttpStatus() { return this.httpStatus; }
    }
}
