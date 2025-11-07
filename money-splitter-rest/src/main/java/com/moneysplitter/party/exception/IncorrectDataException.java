package com.moneysplitter.party.exception;

import com.moneysplitter.exception.GlobalAppException;
import org.springframework.http.HttpStatus;

public class IncorrectDataException extends GlobalAppException {
    public IncorrectDataException(String message) {
        super(message, HttpStatus.BAD_REQUEST, -1005);
    }
}
