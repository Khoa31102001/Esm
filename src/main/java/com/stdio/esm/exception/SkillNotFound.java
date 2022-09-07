package com.stdio.esm.exception;

public class SkillNotFound extends RuntimeException {
    public SkillNotFound() {
    }

    public SkillNotFound(String message) {
        super(message);
    }

    public SkillNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public SkillNotFound(Throwable cause) {
        super(cause);
    }

    public SkillNotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
