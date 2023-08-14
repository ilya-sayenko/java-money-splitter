package org.example.moneysplitter.exception;

import org.springframework.http.HttpStatus;

public class GlobalAppException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final int errorCode;

    public GlobalAppException(HttpStatus httpStatus, int errorCode) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }

    public GlobalAppException(String message, HttpStatus httpStatus, int errorCode) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
