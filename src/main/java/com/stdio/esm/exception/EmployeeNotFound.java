package com.stdio.esm.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class EmployeeNotFound extends RuntimeException {
    public EmployeeNotFound() {
    }

    public EmployeeNotFound(String message) {
        super(message);
    }

    public EmployeeNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public EmployeeNotFound(Throwable cause) {
        super(cause);
    }

    public EmployeeNotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
