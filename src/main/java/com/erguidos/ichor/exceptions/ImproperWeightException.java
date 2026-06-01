package com.erguidos.ichor.exceptions;

public class ImproperWeightException extends Exception {
    private static final long serialVersionUID = 1L;
    
    public ImproperWeightException() {
        super();
    }
    
    public ImproperWeightException(String msg) {
        super(msg);
    }
}
