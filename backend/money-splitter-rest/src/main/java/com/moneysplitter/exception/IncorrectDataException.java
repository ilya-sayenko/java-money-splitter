package com.moneysplitter.exception;

import org.springframework.http.HttpStatus;

public class IncorrectDataException extends GlobalAppException {

    public IncorrectDataException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
