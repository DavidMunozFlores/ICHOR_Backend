package com.erguidos.ichor.exceptions;

public class BlankStringException extends Exception {
    private static final long serialVersionUID = 1L;

    public BlankStringException() {
        super();
    }
    
    public BlankStringException(String msg) {
        super(msg);
    }
}
