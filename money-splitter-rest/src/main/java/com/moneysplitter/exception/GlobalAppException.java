package com.moneysplitter.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
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
}
