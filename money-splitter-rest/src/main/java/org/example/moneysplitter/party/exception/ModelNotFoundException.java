package org.example.moneysplitter.party.exception;

import org.example.moneysplitter.exception.GlobalAppException;
import org.springframework.http.HttpStatus;

public class ModelNotFoundException extends GlobalAppException {
    public ModelNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND, -1004);
    }
}
