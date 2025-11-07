package com.moneysplitter.exception;

import org.springframework.http.HttpStatus;

public class ModelNotFoundException extends GlobalAppException {

    public ModelNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
