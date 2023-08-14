package org.example.moneysplitter.rest.exception;

import org.example.moneysplitter.rest.exception.handler.GlobalAppException;
import org.springframework.http.HttpStatus;

public class ModelNotFoundException extends GlobalAppException {
    public ModelNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND, -1004);
    }
}
