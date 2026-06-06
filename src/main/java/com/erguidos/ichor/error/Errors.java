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
        ALREADY_EXISTS(409);

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
        NOT_EXISTS(404);

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
        ALREADY_EXISTS(409);

        private final int httpStatus;
        User(int httpStatus) { this.httpStatus = httpStatus; }
        @Override public int getHttpStatus() { return this.httpStatus; }
    }
}
