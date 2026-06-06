package com.erguidos.ichor.exceptions;

public class BadBloodTypeException extends IllegalArgumentException {
    private static final long serialVersionUID = 1L;
    
    private static final String NULL_MESSAGE = "Blood type cannot be null";
    private static final String BAD_VALUE_MESSAGE = "Unknown blood type: %s";
    
    public BadBloodTypeException() {
        super(NULL_MESSAGE);
    }
    public BadBloodTypeException(String value) {
        super(String.format(BAD_VALUE_MESSAGE, value));
    }
}
