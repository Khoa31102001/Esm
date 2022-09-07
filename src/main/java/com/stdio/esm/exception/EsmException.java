package com.stdio.esm.exception;

public class EsmException extends Exception {

    public EsmException() {
        super();
    }

    public EsmException(String message) {
        super(message);
    }

    public EsmException(String message, Exception cause) {
        super(message, cause);
    }

    public EsmException(Exception cause) {
        super(cause);
    }

    protected EsmException(String message, Exception cause,
                           boolean enableSuppression,
                           boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
