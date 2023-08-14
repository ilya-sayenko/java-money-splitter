package org.example.moneysplitter.rest.exception;

import org.example.moneysplitter.rest.exception.handler.GlobalAppException;
import org.springframework.http.HttpStatus;

public class IncorrectDeleteException extends GlobalAppException {
    public IncorrectDeleteException(String message) {
        super(message, HttpStatus.BAD_REQUEST, -1006);
    }
}
