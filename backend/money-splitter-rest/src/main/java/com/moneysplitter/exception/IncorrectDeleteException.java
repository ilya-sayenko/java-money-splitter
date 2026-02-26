package com.moneysplitter.exception;

import org.springframework.http.HttpStatus;

public class IncorrectDeleteException extends GlobalAppException {

    public IncorrectDeleteException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
