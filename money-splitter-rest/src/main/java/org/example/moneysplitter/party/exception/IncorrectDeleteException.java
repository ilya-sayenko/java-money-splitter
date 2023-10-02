package org.example.moneysplitter.party.exception;

import org.example.moneysplitter.exception.GlobalAppException;
import org.springframework.http.HttpStatus;

public class IncorrectDeleteException extends GlobalAppException {
    public IncorrectDeleteException(String message) {
        super(message, HttpStatus.BAD_REQUEST, -1006);
    }
}
