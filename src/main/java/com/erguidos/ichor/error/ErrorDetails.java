package com.erguidos.ichor.error;

import java.util.function.Supplier;

import com.erguidos.ichor.exceptions.ErrorCodeException;

public interface ErrorDetails {
    int getHttpStatus();
    String name();

    default ErrorCodeException asException() {
        return new ErrorCodeException(this);
    }
    
    default Supplier<ErrorCodeException> asSupplier() {
        return () -> this.asException();
    }
}
