package org.example.moneysplitter.console.exception.global;

public class GlobalAppException extends RuntimeException {
    public GlobalAppException() {
    }

    public GlobalAppException(String message) {
        super(message);
    }

    public GlobalAppException(String message, Throwable cause) {
        super(message, cause);
    }

    public GlobalAppException(Throwable cause) {
        super(cause);
    }

    public GlobalAppException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
