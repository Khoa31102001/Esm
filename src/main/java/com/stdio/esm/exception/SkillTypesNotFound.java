package com.stdio.esm.exception;

public class SkillTypesNotFound extends RuntimeException {
    public SkillTypesNotFound() {
    }

    public SkillTypesNotFound(String message) {
        super(message);
    }

    public SkillTypesNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public SkillTypesNotFound(Throwable cause) {
        super(cause);
    }

    public SkillTypesNotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
