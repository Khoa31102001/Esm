package com.stdio.esm.exception;

public class RoleNotFound extends RuntimeException {
    public RoleNotFound() {
    }

    public RoleNotFound(String message) {
        super(message);
    }

    public RoleNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public RoleNotFound(Throwable cause) {
        super(cause);
    }

    public RoleNotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
